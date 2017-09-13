package org.alexdev.icarus.game.room;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import org.alexdev.icarus.dao.mysql.groups.GroupDao;
import org.alexdev.icarus.dao.mysql.item.ItemDao;
import org.alexdev.icarus.dao.mysql.pets.PetDao;
import org.alexdev.icarus.dao.mysql.room.RoomDao;
import org.alexdev.icarus.dao.mysql.room.RoomModelDao;
import org.alexdev.icarus.game.entity.EntityType;
import org.alexdev.icarus.game.entity.Entity;
import org.alexdev.icarus.game.entity.EntityStatus;
import org.alexdev.icarus.game.furniture.interactions.InteractionType;
import org.alexdev.icarus.game.furniture.interactions.types.TeleportInteractor;
import org.alexdev.icarus.game.groups.Group;
import org.alexdev.icarus.game.groups.GroupManager;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.ItemType;
import org.alexdev.icarus.game.pets.Pet;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.player.PlayerManager;
import org.alexdev.icarus.game.plugins.PluginEvent;
import org.alexdev.icarus.game.plugins.PluginManager;
import org.alexdev.icarus.game.room.model.RoomMapping;
import org.alexdev.icarus.game.room.model.RoomModel;
import org.alexdev.icarus.game.room.settings.RoomState;
import org.alexdev.icarus.game.room.settings.RoomType;
import org.alexdev.icarus.log.Log;
import org.alexdev.icarus.messages.MessageComposer;
import org.alexdev.icarus.messages.incoming.room.RoomPromotionMessageComposer;
import org.alexdev.icarus.messages.outgoing.groups.GroupBadgesMessageComposer;
import org.alexdev.icarus.messages.outgoing.groups.NewGroupMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.FloorMapMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.OwnerRightsMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.HeightMapMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.RoomDataMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.RoomModelMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.RoomOwnerRightsComposer;
import org.alexdev.icarus.messages.outgoing.room.RoomRatingMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.RightsLevelMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.RoomSpacesMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.WallOptionsMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.items.FloorItemsMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.items.WallItemsMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.notify.GenericDoorbellMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.notify.GenericErrorMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.notify.GenericNoAnswerDoorbellMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.notify.RoomEnterErrorMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.user.CarryObjectComposer;
import org.alexdev.icarus.messages.outgoing.room.user.DanceMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.user.HotelViewMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.user.RemoveUserMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.user.RoomForwardComposer;
import org.alexdev.icarus.messages.outgoing.room.user.UserDisplayMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.user.UserStatusMessageComposer;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class Room {

    private AtomicInteger privateId = new AtomicInteger(-1);
    private RoomData data;
    private RoomScheduler scheduler;
    private RoomMapping mapping;
    private RoomModel model;
    private RoomPromotion promotion;

    private List<Entity> entities; 
    private Map<Integer, Item> items;
    private List<Integer> rights;


    public Room() {

        this.data = new RoomData(this);

        this.items = Maps.newHashMap();
        this.entities = Lists.newArrayList();

        this.rights = RoomDao.getRoomRights(this.getData().getId());
    }

    public void loadRoom(Player player, String pass) {

        if (this.getModel() == null) {
            Log.println("The specified room model (" + this.data.getModel() + ") does not exist.");
            return;
        }

        if (this.getModel().getDoorLocation() == null) {
            Log.println("Failed to load door configuration.");
            return;
        }

        this.loadRoom(player, pass, 
                this.getModel().getDoorLocation().getX(), 
                this.getModel().getDoorLocation().getY(), 
                this.getModel().getDoorLocation().getRotation());
    }

    public void loadRoom(Player player, String pass, int x, int y, int rotation) {

        if (player.inRoom()) {
            player.leaveRoom(false);
        }

        boolean isOwner = this.hasRights(player, true);

        if (this.data.getUsersNow() >= this.data.getUsersMax()) {
            if (!player.hasPermission("user_enter_full_rooms")) {
                if (player.getDetails().getId() != this.data.getOwnerId()) {
                    player.send(new RoomEnterErrorMessageComposer(1));
                    return;
                }
            }
        }

        if (player.getRoomUser().isTeleporting()) {
            if (player.getRoomUser().getTeleportRoomId() != this.data.getId()) {
                player.leaveRoom(true);
            } else {
                player.getRoomUser().setTeleporting(false);
                player.getRoomUser().setTeleportRoomId(0);
            }
        }
        else {

            if (this.data.getState().getStateCode() > 0 && !this.hasRights(player, false)) {
                if (this.data.getState() == RoomState.DOORBELL) {

                    if (this.getPlayers().size() > 0) {
                        player.send(new GenericDoorbellMessageComposer(1));
                        this.send(new GenericDoorbellMessageComposer(player.getDetails().getName()), true);
                    } else {
                        player.send(new GenericNoAnswerDoorbellMessageComposer());
                    }

                    return;
                }

                if (this.data.getState() == RoomState.PASSWORD) {
                    if (!pass.equals(this.data.getPassword())) {
                        player.send(new GenericErrorMessageComposer(-100002));
                        return;
                    }
                }

                if (this.data.getState() == RoomState.INVISIBLE) {
                    player.leaveRoom(true);
                    return;
                }
            }
        }

        RoomUser roomUser = player.getRoomUser();

        roomUser.setRoom(this);
        roomUser.getStatuses().clear();

        player.send(new RoomModelMessageComposer(this.getModel().getName(), this.getData().getId()));
        player.send(new RoomRatingMessageComposer(this.data.getScore()));

        int floorData = Integer.parseInt(this.data.getFloor());
        int wallData = Integer.parseInt(this.data.getWall());

        if (floorData > 0) {
            player.send(new RoomSpacesMessageComposer("floor", this.data.getFloor()));
        }

        if (wallData > 0) {
            player.send(new RoomSpacesMessageComposer("wallpaper", this.data.getWall()));
        }

        player.send(new RoomSpacesMessageComposer("landscape", this.data.getLandscape()));
        player.send(new RoomOwnerRightsComposer(this.data.getId(), isOwner));

        if (roomUser.getRoom().hasRights(player, true)) {
            player.send(new RightsLevelMessageComposer(4));
            player.send(new OwnerRightsMessageComposer());

        } else if (roomUser.getRoom().hasRights(player, false)) {
            player.send(new RightsLevelMessageComposer(1));

        } else {
            player.send(new RightsLevelMessageComposer(0));
        }

        roomUser.setVirtualId(this.privateId.incrementAndGet());
        roomUser.getPosition().setX(this.getModel().getDoorLocation().getX());
        roomUser.getPosition().setY(this.getModel().getDoorLocation().getY());
        roomUser.getPosition().setZ(this.getModel().getHeight(roomUser.getPosition().getX(), roomUser.getPosition().getY()));
        roomUser.getPosition().setRotation(this.getModel().getDoorLocation().getRotation());

        if (!(this.getPlayers().size() > 0)) {

            this.items = ItemDao.getRoomItems(this.getData().getId());
            
            this.mapping = new RoomMapping(this);
            this.mapping.regenerateCollisionMaps();

            this.scheduler = new RoomScheduler(this);
            this.scheduler.scheduleTasks();

            this.addPets();

            boolean isCancelled = PluginManager.callEvent(PluginEvent.ROOM_FIRST_ENTRY_EVENT, new LuaValue[] { CoerceJavaToLua.coerce(player), CoerceJavaToLua.coerce(this) });

            if (isCancelled) {
                player.leaveRoom(true);
            }
        }
    }

    public void loadMapData(Player player) {

        player.send(new HeightMapMessageComposer(this.getModel()));
        player.send(new FloorMapMessageComposer(this));

        this.send(new UserDisplayMessageComposer(player));
        this.send(new UserStatusMessageComposer(player));

        if (!this.getEntities().contains(player)) {
            this.getEntities().add(player);
            this.getData().updateUsersNow();
        }

        player.send(new UserDisplayMessageComposer(this.getEntities()));
        player.send(new UserStatusMessageComposer(this.getEntities()));

        for (Player players : this.getPlayers()) {
            if (players.getRoomUser().isDancing()) {
                player.send(new DanceMessageComposer(players.getRoomUser().getVirtualId(), players.getRoomUser().getDanceId()));
            }

            if (players.getRoomUser().getCarryItem() > 0) {
                player.send(new CarryObjectComposer(players.getRoomUser().getVirtualId(), players.getRoomUser().getCarryItem())); 
            }
        }

        /* [f76a21e6be54cea897c44fbfc7c32839]
Outgoing[2923, _-3yD] -> [0][0][0][10][11]k[0][0][0][5][0][0][0][7]
{id:2923}{i:5}{i:7}
--------------------
[1d1cb11cb8d5156afeb284fb1eb04339]
Incoming[2241, _-4nq, UserUpdateMessageParser] <- [0][0][0]9[8]Á[0][0][0][1][0][0][0][0][0][0][0][6][0][0][0][6][0][3]0.0[0][0][0][5][0][0][0][5][0]/flatctrl 4/mv 5,7,0.0//
--------------------
[1d1cb11cb8d5156afeb284fb1eb04339]
Incoming[2241, _-4nq, UserUpdateMessageParser] <- [0][0][0].[8]Á[0][0][0][1][0][0][0][0][0][0][0][5][0][0][0][7][0][3]0.0[0][0][0][5][0][0][0][5][0][13]/flatctrl 4//        */

        if (player.hasPermission("room_all_rights") || this.data.getOwnerId() == player.getDetails().getId()) {
            player.getRoomUser().setStatus(EntityStatus.FLAT_CONTROL, "4");
        } else if (this.hasRights(player, false)) {
            player.getRoomUser().setStatus(EntityStatus.FLAT_CONTROL, "1");
        }        

        player.send(new WallOptionsMessageComposer(this.getData().hasHiddenWall(), this.getData().getWallThickness(), this.getData().getFloorThickness()));
        player.send(new RoomPromotionMessageComposer(this));

        this.fixFlashingTeleporters();

        player.send(new FloorItemsMessageComposer(this.getFloorItems()));
        player.send(new WallItemsMessageComposer(this.getWallItems()));
        player.send(new RoomDataMessageComposer(this, player, true, false));

        player.getMessenger().sendStatus(false);

        boolean isCancelled = PluginManager.callEvent(PluginEvent.ROOM_ENTER_EVENT, new LuaValue[] { CoerceJavaToLua.coerce(player), CoerceJavaToLua.coerce(this) });

        if (isCancelled) {
            player.leaveRoom(true);
        }

        Group group = this.getGroup();

        if (group != null) {
            if (player.getRoomUser().getMetadata().getAsBool("showGroupHomeroomDialog")) {
                player.getRoomUser().getMetadata().set("showGroupHomeroomDialog", false);
                
                this.send(new NewGroupMessageComposer(this.getData().getId(), this.data.getGroupId()));
            }
        }
    }

    public void addEntity(Entity entity) {

        this.addEntity(entity, 
                this.getModel().getDoorLocation().getX(), 
                this.getModel().getDoorLocation().getY(), 
                this.getModel().getDoorLocation().getRotation());
    }

    public void addEntity(Entity entity, int x, int y, int rotation) {

        if (entity.getType() == EntityType.PLAYER) {
            return;
        }

        RoomUser roomUser = entity.getRoomUser();

        roomUser.setRoom(this);
        roomUser.setVirtualId(this.privateId.incrementAndGet());
        roomUser.getPosition().setX(x);
        roomUser.getPosition().setY(y);
        roomUser.getPosition().setZ(this.getModel().getHeight(roomUser.getPosition().getX(), roomUser.getPosition().getY()));
        roomUser.getPosition().setRotation(rotation);

        this.send(new UserDisplayMessageComposer(entity));
        this.send(new UserStatusMessageComposer(entity));

        if (!this.getEntities().contains(entity)) {
            this.getEntities().add(entity);
        }

        this.mapping.getTile(x, y).setEntity(entity);
    }

    public void removeEntity(Entity entity) {

        if (this.entities != null) {
            this.entities.remove(entity);
            this.data.updateUsersNow();
        }

        if (this.getPlayers().size() > 0) {
            this.send(new RemoveUserMessageComposer(entity.getRoomUser().getVirtualId()));
        }

        if (entity.getType() != EntityType.PLAYER) {

            // Save coordinates of pet
            if (entity.getType() == EntityType.PET) {
                ((Pet)entity).savePosition();
            }

            entity.dispose();
        }

        entity.getRoomUser().dispose();
    }

    public boolean hasRights(Player player, boolean ownerCheckOnly) {

        if (player.hasPermission("room_all_rights")) {
            return true;
        }

        return hasRights(player.getDetails().getId(), ownerCheckOnly);
    }

    public boolean hasRights(int userId, boolean ownerCheckOnly) {

        if (this.data.getOwnerId() == userId) {
            return true;
        } else {
            if (!ownerCheckOnly) {
                return this.rights.contains(Integer.valueOf(userId));
            }
        }

        return false;
    }
    

    /**
     * Sometimes the teleporters will glitch out when placed, due to whatever reason and they will flash
     * or either have their door open (presumably because of low room Id numbers - like room Id 1 or 2)
     * 
     * This will fix that issue.
     * 
     * @return none
     */
    private void fixFlashingTeleporters() {
        for (Item item : this.getItems(InteractionType.TELEPORT)) {
            item.setExtraData(TeleportInteractor.TELEPORTER_CLOSE);
        }

    }

    /**
     * Select room pets from data access object, will add them
     * along with setting their stored coordinates to the entity and will also apply the height.
     * 
     * @return none
     */
    private void addPets() {
        for (Pet pet : PetDao.getRoomPets(this.data.getId())) {
            pet.getRoomUser().setRoom(this);
            pet.getRoomUser().setVirtualId(this.privateId.incrementAndGet());
            pet.getRoomUser().getPosition().setX(pet.getX());
            pet.getRoomUser().getPosition().setY(pet.getY());
            pet.getRoomUser().getPosition().setZ(this.getModel().getHeight(pet.getRoomUser().getPosition().getX(), pet.getRoomUser().getPosition().getY()));
            pet.getRoomUser().getPosition().setRotation(0);
            this.entities.add(pet);
        }
    }

    public void send(MessageComposer response, boolean checkRights) {

        for (Player player : this.getPlayers()) {

            if (checkRights && this.hasRights(player, false)) {
                player.send(response);
            }
        }
    }

    public void send(MessageComposer response) {

        for (Player player : this.getPlayers()) {
            player.send(response);
        }
    }

    public List<Player> getPlayers() {

        List<Player> sessions = new ArrayList<Player>();

        for (Entity entity : this.getEntities(EntityType.PLAYER)) {
            Player player = (Player)entity;
            sessions.add(player);
        }

        return sessions;
    }

    public List<Entity> getEntities(EntityType type) {
        List<Entity> e = new ArrayList<Entity>();

        for (Entity entity : this.entities) {
            if (entity.getType() == type) {
                e.add(entity);
            }
        }

        return e;
    }


    public Entity getEntityById(int id) {

        for (Entity entity : this.entities) {
            if (entity.getDetails().getId() == id) {
                return entity;
            }
        }

        return null;
    }

    public List<Item> getFloorItems() {

        List<Item> floorItems = items.values().stream()
                .filter(item -> item.getType() == ItemType.FLOOR)
                .collect(Collectors.toList());

        return floorItems;
    }

    public List<Item> getWallItems() {

        List<Item> wallItems = items.values().stream()
                .filter(item -> item.getType() == ItemType.WALL)
                .collect(Collectors.toList());

        return wallItems;
    }

    public List<Item> getItems(InteractionType interactionType) {

        try {
            return items.values().stream()
                    .filter(item -> item.getDefinition().getInteractionType() == interactionType)
                    .collect(Collectors.toList());

        } catch (Exception e) {
            return Lists.newArrayList();
        }
    }

    public Item getItem(int itemId) {

        if (this.items.containsKey(itemId)) {
            return this.items.get(itemId);
        }

        return ItemDao.getItem(itemId);
    }

    public Map<Integer, Item> getItems() {
        return this.items;
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public RoomData getData() {
        return data;
    }

    public RoomModel getModel() {

        if (this.data.getModel().startsWith("dynamic_model")) {

            if (this.model == null) {
                this.model = RoomModelDao.getCustomModel(this.data.getId());
            }

            return model;
        }

        return RoomModelDao.getModel(this.data.getModel());
    }

    public RoomMapping getMapping() {
        return mapping;
    }

    public void save() {
        RoomDao.updateRoom(this);
    }

    public void setModel(RoomModel model) {
        this.model = model;
    }

    public static void sendToRoom(Player user, int roomId) {
        user.send(new RoomForwardComposer(roomId));
    }

    public void createPromotion(String promotionName, String promotionDescription) {
        this.promotion = new RoomPromotion(this, promotionName, promotionDescription);
        RoomManager.addPromotedRoom(this.data.getId(), this);
    }

    public void endPromotion() {
        this.promotion = null;
        RoomManager.removePromotedRoom(this.data.getId());
    }

    public boolean hasPromotion() {
        return this.promotion != null;
    }

    public RoomPromotion getPromotion() {
        return this.promotion;
    }

    public List<Integer> getRights() {
        return this.rights;
    }

    public Group getGroup() {

        if (this.data.getGroupId() > 0) {
            
            Group group = GroupDao.getGroup(this.data.getGroupId());

            if (group == null) {
                this.data.setGroupId(0);
                this.save();
                return null;
            }

            return group;
        }

        return null;
    }
    

    public void dispose(boolean forceDisposal) {

        if (forceDisposal) {

            this.cleanupRoomData();
            RoomManager.removeRoom(this.getData().getId());

        } else {

            if (this.getPlayers().size() > 0) {
                return;
            }

            this.cleanupRoomData();

            if (PlayerManager.getById(this.data.getOwnerId()) == null && this.data.getRoomType() == RoomType.PRIVATE) { 
                RoomManager.removeRoom(this.getData().getId());
            }
        }
    }

    private void cleanupRoomData() {

        if (this.scheduler != null) {
            this.scheduler.cancelTasks();
        }

        if (this.entities != null) {

            for (int i = 0; i < this.entities.size(); i++) {
                Entity entity = this.entities.get(i);

                if (entity.getType() != EntityType.PLAYER) {
                    this.removeEntity(entity);
                }
            }

            this.entities.clear();
        }

        if (this.rights != null) {
            this.rights.clear();
        }
        
        this.scheduler  = null;
        this.mapping = null;
        this.promotion = null;

        this.privateId.set(-1);
    }

}
