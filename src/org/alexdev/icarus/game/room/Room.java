package org.alexdev.icarus.game.room;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.alexdev.icarus.dao.mysql.room.RoomDao;
import org.alexdev.icarus.dao.mysql.room.RoomModelDao;
import org.alexdev.icarus.game.groups.Group;
import org.alexdev.icarus.game.groups.GroupManager;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.player.PlayerManager;
import org.alexdev.icarus.game.room.enums.RoomType;
import org.alexdev.icarus.game.room.managers.*;
import org.alexdev.icarus.game.room.model.*;
import org.alexdev.icarus.game.room.scheduler.*;
import org.alexdev.icarus.game.room.tasks.*;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.util.Metadata;

public class Room extends Metadata {

    private RoomData data;
    private RoomModel model;
    private RoomScheduler scheduler;
    private RoomMapping mapping;
    private RoomPromotion promotion;
    private RoomItemManager itemManager;
    private RoomEntityManager entityManager;
    private Group group;
    private List<Integer> rights;

    public Room(RoomData data) {
        this.data = data;
        this.mapping = new RoomMapping(this);
        this.scheduler = new RoomScheduler(this);
        this.itemManager = new RoomItemManager(this);
        this.entityManager = new RoomEntityManager(this);
        this.rights = RoomDao.getRoomRights(this.data.getId());
    }

    /**
     * Register the tasks required for room functionality
     */
    public void scheduleEvents() {
        this.scheduler.scheduleEvent(1, TimeUnit.SECONDS, TaskType.REPEAT, new CarryItemTask(this));
        this.scheduler.scheduleEvent(4, TimeUnit.SECONDS, TaskType.REPEAT, new RollerTask(this));
        this.scheduler.scheduleEvent(5, TimeUnit.SECONDS, TaskType.REPEAT, new PetTask(this));
        this.scheduler.scheduleTasks();
    }

    /**
     * Checks for rights, including ownership.
     *
     * @param userId the user id
     * @param ownerCheckOnly the owner check only
     * @return true, if successful
     */
    public boolean hasRights(int userId) {

        if (this.data.getOwnerId() == userId) {
            return true;
        } else {
            return this.rights.contains(Integer.valueOf(userId));
        }
    }

    /**
     * Checks for ownership.
     *
     * @param userId the user id
     * @return true, if successful
     */
    public boolean hasOwnership(int userId) {
        return this.data.getOwnerId() == userId;
    }

    /**
     * Send with rights.
     *
     * @param response the response
     */
    public void sendWithRights(MessageComposer response) {

        for (Player player : this.getEntityManager().getPlayers()) {

            if (this.hasRights(player.getEntityId())) {
                player.send(response);
            }
        }
    }

    /**
     * Send.
     *
     * @param response the response
     */
    public void send(MessageComposer response) {

        for (Player player : this.getEntityManager().getPlayers()) {
            player.send(response);
        }
    }

    /**
     * Gets the model.
     *
     * @return the model
     */
    public RoomModel getModel() {

        if (this.data.getModel().startsWith("dynamic_model")) {

            if (this.model == null) {
                this.model = RoomModelDao.getCustomModel(this.data.getId());
            }

            return model;
        }

        return RoomModelDao.getModel(this.data.getModel());
    }

    /**
     * Save.
     */
    public void save() {
        RoomDao.update(this);
    }

    /**
     * Save the room metadata, will only call if there's objects that allow to be saved, exist.
     */
    public void saveMetadata() {

        if (this.getMetadata().getMap().size() > 0) {
            RoomDao.updateMetadata(this);
        }
    }

    /**
     * Sets the model.
     *
     * @param model the new model
     */
    public void setModel(RoomModel model) {
        this.model = model;
    }

    /**
     * Creates the promotion.
     *
     * @param promotionName the promotion name
     * @param promotionDescription the promotion description
     */
    public void createPromotion(String promotionName, String promotionDescription) {
        this.promotion = new RoomPromotion(this, promotionName, promotionDescription);
        RoomManager.addPromotedRoom(this.data.getId(), this);
    }

    /**
     * End promotion.
     */
    public void endPromotion() {
        this.promotion = null;
        RoomManager.removePromotedRoom(this.data.getId());
    }

    /**
     * Checks for promotion.
     *
     * @return true, if successful
     */
    public boolean hasPromotion() {
        return this.promotion != null;
    }

    /**
     * Gets the group, if the room has no users, it will only load
     * group information straight from the database.
     *
     * @return the group
     */
    public Group getGroup() {

        if (this.group == null) {
            if (this.data.getGroupId() > 0) {
                return GroupManager.getGroup(this.data.getGroupId());
            } else {
                return null;
            }
        }

        return this.group;
    }

    /**
     * Sets the group.
     *
     * @param group the new group
     */
    public void setGroup(Group group) {
        this.group = group;
    }

    /**
     * Load the full group information, called when
     * the first player joins a room.
     * 
     * Calls GroupManager.loadGroup()
     */
    public void loadGroup() {
        this.group = GroupManager.loadGroup(this.data.getGroupId());
    }


    /**
     * Load the full group information, called when
     * the first player joins a room.
     * 
     * Calls GroupManager.unloadGroup()
     */
    public void unloadGroup() {
        if (this.group != null) {
            this.group = null;
            GroupManager.unloadGroup(this.data.getGroupId());
        }
    }

    /**
     * Will try to cleanup the room if the following variables are met.
     * 
     * The first being that there's no players in the room.
     * The second being that this is a private room.
     * 
     * The third being that the owner of the room 
     * is offline (if this parameter is met then the room will be unloaded).
     */
    public void dispose() {

        if (this.entityManager.getPlayers().size() > 0) {
            return;
        }

        if (this.scheduler != null) {
            this.scheduler.cancelTasks();
        }

        this.entityManager.cleanupNonPlayableEntities();
        this.itemManager.getItems().clear();
        this.unloadGroup();

        if (this.data.getRoomType() == RoomType.PUBLIC) {
            return;
        }

        if (PlayerManager.hasPlayer(this.data.getOwnerId())) {
            return;
        }

        RoomManager.removeRoom(this.data.getId());
        this.destroyObjects();
    }

    private void destroyObjects() {
        this.entityManager = null;
        this.scheduler = null;
        this.itemManager = null;
        this.promotion = null;
        this.data = null;
        this.mapping = null;
        this.model = null;
    }

    /**
     * Gets the data.
     *
     * @return the data
     */
    public RoomData getData() {
        return data;
    }

    /**
     * Gets the mapping.
     *
     * @return the mapping
     */
    public RoomMapping getMapping() {
        return mapping;
    }

    /**
     * Gets the promotion.
     *
     * @return the promotion
     */
    public RoomPromotion getPromotion() {
        return promotion;
    }

    /**
     * Gets the rights.
     *
     * @return the rights
     */
    public List<Integer> getRights() {
        return rights;
    }

    /**
     * Gets the entity manager.
     *
     * @return the entity manager
     */
    public RoomEntityManager getEntityManager() {
        return entityManager;
    }

    /**
     * Gets the item manager.
     *
     * @return the item manager
     */
    public RoomItemManager getItemManager() {
        return itemManager;
    }

    /**
     * Gets the scheduler.
     *
     * @return the scheduler
     */
    public RoomScheduler getScheduler() {
        return scheduler;
    }

}