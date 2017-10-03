package org.alexdev.icarus.game.room.managers;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.alexdev.icarus.dao.mysql.pets.PetDao;
import org.alexdev.icarus.game.entity.Entity;
import org.alexdev.icarus.game.entity.EntityType;
import org.alexdev.icarus.game.pets.Pet;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.plugins.PluginEvent;
import org.alexdev.icarus.game.plugins.PluginManager;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.enums.RoomAction;
import org.alexdev.icarus.game.room.user.RoomUser;
import org.alexdev.icarus.messages.outgoing.room.user.RemoveUserMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.user.UserDisplayMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.user.UserStatusMessageComposer;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

public class RoomEntityManager {

    private Room room;
    private List<Entity> entities; 

    public RoomEntityManager(Room room) {
        this.room = room;
        this.entities = new ArrayList<>();
    }

    /**
     * Adds an {@link Entity} to the room with the default door coordinates.
     * The entity will appear to everybody who is in the room.
     *
     * @param entity the entity
     */
    public void addEntity(Entity entity) {

        this.addEntity(entity, 
                this.room.getModel().getDoorLocation().getX(), 
                this.room.getModel().getDoorLocation().getY(), 
                this.room.getModel().getDoorLocation().getRotation());
    }

    /**
     * Adds an {@link Entity} to the room with specified x, y coordinates and rotation.
     * The entity will appear to everybody who is in the room.
     * 
     * @param entity - {@link Entity} the entity to add to the room
     * @param x - {@link int} the x coordinate
     * @param y - {@link int} the y coordinate
     * @param rotation - {@link int} the rotation of the entity
     */
    public void addEntity(Entity entity, int x, int y, int rotation) {

        RoomUser roomUser = entity.getRoomUser();

        roomUser.setRoom(this.room);
        roomUser.setVirtualId(this.generateVirtualId());
        roomUser.getPosition().setX(x);
        roomUser.getPosition().setY(y);
        roomUser.getPosition().setRotation(rotation);
        roomUser.updateNewHeight(roomUser.getPosition());

        this.room.send(new UserDisplayMessageComposer(Arrays.asList(new Entity[] { entity })));
        this.room.send(new UserStatusMessageComposer(Arrays.asList(new Entity[] { entity })));

        if (!this.entities.contains(entity)) {
            this.entities.add(entity);
            this.room.getData().setUsersNow(this.getPlayers().size());
        }

        this.room.getMapping().getTile(x, y).addEntity(entity);
        
        if (entity.getType() == EntityType.PLAYER) {
            if (this.room.getEntityManager().getPlayers().size() == 0) {
                this.beginRoomEntry(entity);
            }
        }
    }

    /**
     * Begin room entry.
     *
     * @param entity the entity
     */
    private void beginRoomEntry(Entity entity) {

        Player player = (Player) entity;;
        
        this.room.getItemManager().refreshRoomFurniture();
        this.room.getMapping().regenerateCollisionMaps();
        this.room.scheduleEvents();
        this.room.getEntityManager().addPets();
        this.room.loadGroup();

        boolean isCancelled = PluginManager.callEvent(PluginEvent.ROOM_FIRST_ENTRY_EVENT, new LuaValue[] { 
            CoerceJavaToLua.coerce(player), 
            CoerceJavaToLua.coerce(this.room) 
        });

        if (isCancelled) {
            player.performRoomAction(RoomAction.LEAVE_ROOM, true);
        }
    }

    /**
     * Retrieves the pet data for a room and adds them into the class
     * with their saved coordinates from the database.
     */
    public void addPets() {
        for (Pet pet : PetDao.getRoomPets(this.room.getData().getId())) {
            pet.getRoomUser().setRoom(this.room);
            pet.getRoomUser().setVirtualId(this.generateVirtualId());
            pet.getRoomUser().getPosition().setX(pet.getX());
            pet.getRoomUser().getPosition().setY(pet.getY());
            pet.getRoomUser().getPosition().setRotation(0);
            pet.getRoomUser().updateNewHeight(pet.getRoomUser().getPosition());
            this.entities.add(pet);
        }
    }

    /**
     * Removes the given entity from the class, it will
     * remove them from the entity list, and show everybody that
     * the entity has disappeared.
     * 
     * If the entity was a pet or a bot, then their coordinates
     * will be saved to the database.
     * 
     * @param entity - {@link Entity}
     */
    public void removeEntity(Entity entity) {

        if (this.entities != null) {
            this.entities.remove(entity);
            this.room.getData().setUsersNow(this.getPlayers().size());
        }

        if (this.getPlayers().size() > 0) {
            this.room.send(new RemoveUserMessageComposer(entity.getRoomUser().getVirtualId()));
        }

        if (entity.getType() == EntityType.PET) {
            Pet pet = (Pet)entity;
            pet.savePosition();
        }

        entity.getRoomUser().clearUserData();
    }


    /**
     * Cleanup non playable entities.
     * 
     * Will save the coordinates of these entities if they were either
     * bots or pets..
     * 
     */
    public void cleanupNonPlayableEntities() {

        if (this.entities != null) {

            List<Entity> nonPlayableEntities = this.getNonPlayable();

            for (int i = 0; i < nonPlayableEntities.size(); i++) {
                Entity entity = nonPlayableEntities.get(i);
                entity.dispose();
            }

            this.entities.clear();
        }
    }

    /**
     * Return the list of players currently in this room.
     *  
     * @return List<{@link Player}> list of players
     */
    public List<Player> getPlayers() {
        return this.getEntitiesByClass(Player.class);
    }

    /**
     * Return the list of non playable entities in this room.
     *  
     * @return List of list of entities
     */
    public List<Entity> getNonPlayable() {
        return this.getEntitiesByType(EntityType.BOT, EntityType.PET);
    }

    /**
     * Return the list of entities currently in this room by its
     * given class.
     *  
     * @param entityClass the entity class
     * @return List<{@link T}> list of entities
     */
    public <T extends Entity> List<T> getEntitiesByClass(Class<T> entityClass) {

        List<T> entities = new ArrayList<>();

        for (Entity entity : this.entities) {

            if (entity.getType().getEntityClass() == entityClass) {
                entities.add(entityClass.cast(entity));
            }
        }

        return entities;
    }

    /**
     * Return the list of entities currently in this room by its
     * given class.
     *  
     *
     * @param EntityType arguments
     * @return List of entities
     */

    public List<Entity> getEntitiesByType(EntityType... types) {

        List<Entity> entities = new ArrayList<>();

        for (Entity entity : this.entities) {
            for (EntityType type : types) {
                if (type.equals(entity.getType())) {
                    entities.add(entity);
                }
            }
        }

        return entities;
    }

    /**
     * Returns an entity by its id and given class.
     *  
     *
     * @param <T> the generic type
     * @param id the id
     * @param entityClass the entity class
     * @return Entity
     */
    public <T extends Entity> T getEntityById(int id, Class<T> entityClass) {

        for (Entity entity : this.entities) {
            if (entity.getType().getEntityClass().isAssignableFrom(entityClass)) {
                if (entity.getDetails().getId() == id) {
                    return entityClass.cast(entity);
                }
            }
        }

        return null;
    }

    /**
     * Generate the virtual id, will reuse old virtual ids that may
     * or may not have been used before.
     *
     * @return the virtual room id
     */
    public int generateVirtualId() {

        int virtualId = 0;

        while (this.getEntityByVirtualId(virtualId) != null) {
            virtualId++;
        }
        
        return virtualId;
    }

    /**
     * Gets the entity by virtual id.
     *
     * @param <T> the generic type
     * @param virtualId the virtual id
     * @param entityClass the entity class
     * @return the entity by virtual id
     */
    public Entity getEntityByVirtualId(int virtualId) {

        for (Entity entity : this.entities) {
            if (entity.getRoomUser().getVirtualId() == virtualId) {
                return entity;
            }
        }

        return null;
    }

    /**
     * Gets the entities.
     *
     * @return the entities
     */
    public List<Entity> getEntities() {
        return entities;
    }
}
