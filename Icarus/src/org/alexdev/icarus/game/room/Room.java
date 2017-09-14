package org.alexdev.icarus.game.room;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.alexdev.icarus.dao.mysql.groups.GroupDao;
import org.alexdev.icarus.dao.mysql.room.RoomDao;
import org.alexdev.icarus.dao.mysql.room.RoomModelDao;
import org.alexdev.icarus.game.groups.Group;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.player.PlayerDetails;
import org.alexdev.icarus.game.player.PlayerManager;
import org.alexdev.icarus.game.room.enums.RoomType;
import org.alexdev.icarus.game.room.managers.RoomEntityManager;
import org.alexdev.icarus.game.room.managers.RoomItemManager;
import org.alexdev.icarus.game.room.model.RoomMapping;
import org.alexdev.icarus.game.room.model.RoomModel;
import org.alexdev.icarus.messages.MessageComposer;

public class Room {

    private AtomicInteger virtualTicketCounter = new AtomicInteger(-1);

    private RoomData data;
    private RoomModel model;
    private RoomScheduler scheduler;
    private RoomMapping mapping;
    private RoomPromotion promotion;
    private RoomItemManager itemManager;
    private RoomEntityManager entityManager;

    private List<Integer> rights;

    public Room() {
        this.data = new RoomData(this);
        this.mapping = new RoomMapping(this);
        this.scheduler = new RoomScheduler(this);
        this.itemManager = new RoomItemManager(this);
        this.entityManager = new RoomEntityManager(this);

        this.rights = RoomDao.getRoomRights(this.data.getId());
    }

    public boolean hasRights(int userId, boolean ownerCheckOnly) {

        PlayerDetails details = PlayerManager.getPlayerData(userId);

        if (details != null) {

            if (details.hasPermission("room_all_rights")) {
                return true;
            }
        }

        if (this.data.getOwnerId() == userId) {
            return true;
        } else {

            if (!ownerCheckOnly) {
                return this.rights.contains(Integer.valueOf(userId));
            }
        }

        return false;
    }

    public void sendWithRights(MessageComposer response) {

        for (Player player : this.getEntityManager().getPlayers()) {

            if (this.hasRights(player.getDetails().getId(), false)) {
                player.send(response);
            }
        }
    }

    public void send(MessageComposer response) {

        for (Player player : this.getEntityManager().getPlayers()) {
            player.send(response);
        }
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

    public void save() {
        RoomDao.updateRoom(this);
    }

    public void setModel(RoomModel model) {
        this.model = model;
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

    public void dispose() {
        
        if (this.entityManager.getPlayers().size() > 0) {
            return;
        }

        this.cleanupRoomData();

        if (this.data.getRoomType() != RoomType.PRIVATE) {
            return;
        }
        
        if (!PlayerManager.hasPlayer(this.data.getOwnerId())) {
            return;
        }
           
        RoomManager.removeRoom(this.data.getId());
    }

    private void cleanupRoomData() {

        if (this.scheduler != null) {
            this.scheduler.cancelTasks();
        }

        this.entityManager.cleanupEntities();
        this.virtualTicketCounter.set(-1);
    }

    public RoomData getData() {
        return data;
    }

    public RoomMapping getMapping() {
        return mapping;
    }

    public RoomPromotion getPromotion() {
        return promotion;
    }

    public List<Integer> getRights() {
        return rights;
    }

    public RoomEntityManager getEntityManager() {
        return entityManager;
    }

    public RoomItemManager getItemManager() {
        return itemManager;
    }

    public AtomicInteger getVirtualTicketCounter() {
        return virtualTicketCounter;
    }

    public RoomScheduler getScheduler() {
        return scheduler;
    }
}