package org.alexdev.icarus.game.room;

import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

import org.alexdev.icarus.dao.mysql.groups.GroupDao;
import org.alexdev.icarus.dao.mysql.room.RoomDao;
import org.alexdev.icarus.dao.mysql.room.RoomModelDao;
import org.alexdev.icarus.game.entity.EntityStatus;
import org.alexdev.icarus.game.groups.Group;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.player.PlayerManager;
import org.alexdev.icarus.game.plugins.PluginEvent;
import org.alexdev.icarus.game.plugins.PluginManager;
import org.alexdev.icarus.game.room.managers.RoomEntityManager;
import org.alexdev.icarus.game.room.managers.RoomItemManager;
import org.alexdev.icarus.game.room.model.RoomMapping;
import org.alexdev.icarus.game.room.model.RoomModel;
import org.alexdev.icarus.game.room.settings.RoomState;
import org.alexdev.icarus.game.room.settings.RoomType;
import org.alexdev.icarus.game.room.user.RoomUser;
import org.alexdev.icarus.log.Log;
import org.alexdev.icarus.messages.MessageComposer;
import org.alexdev.icarus.messages.incoming.room.RoomPromotionMessageComposer;
import org.alexdev.icarus.messages.outgoing.groups.NewGroupMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.FloorMapMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.HeightMapMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.OwnerRightsMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.RightsLevelMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.RoomDataMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.RoomModelMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.RoomOwnerRightsComposer;
import org.alexdev.icarus.messages.outgoing.room.RoomRatingMessageComposer;
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
import org.alexdev.icarus.messages.outgoing.room.user.UserDisplayMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.user.UserStatusMessageComposer;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

public class Room {

    private AtomicInteger privateId = new AtomicInteger(-1);
    
    private RoomData data;
    private RoomModel model;
    private RoomScheduler scheduler;
    private RoomMapping mapping;
    private RoomPromotion promotion;
    

    private List<Integer> rights;
    
    private RoomItemManager itemManager;
    private RoomEntityManager entityManager;

    public Room() {
        this.data = new RoomData(this);
        this.mapping = new RoomMapping(this);
        this.scheduler = new RoomScheduler(this);
        this.itemManager = new RoomItemManager(this);
        this.entityManager = new RoomEntityManager(this);
        
        this.rights = RoomDao.getRoomRights(this.data.getId());
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
            player.performRoomAction(RoomAction.LEAVE_ROOM, false);
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
                player.performRoomAction(RoomAction.LEAVE_ROOM, true);
            } else {
                player.getRoomUser().setTeleporting(false);
                player.getRoomUser().setTeleportRoomId(0);
            }
        }
        else {

            if (this.data.getState().getStateCode() > 0 && !this.hasRights(player, false)) {
                if (this.data.getState() == RoomState.DOORBELL) {

                    if (this.entityManager.getPlayers().size() > 0) {
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
                    player.performRoomAction(RoomAction.LEAVE_ROOM, true);
                    return;
                }
            }
        }

        RoomUser roomUser = player.getRoomUser();

        roomUser.setRoom(this);
        roomUser.getStatuses().clear();

        player.send(new RoomModelMessageComposer(this.getModel().getName(), this.data.getId()));
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

        boolean firstUserEntry = !(this.entityManager.getPlayers().size() > 0);
        
        if (firstUserEntry) {

            this.itemManager.refreshRoomFurniture();
            this.mapping.regenerateCollisionMaps();
            this.scheduler.scheduleTasks();
            this.entityManager.addPets();

            boolean isCancelled = PluginManager.callEvent(PluginEvent.ROOM_FIRST_ENTRY_EVENT, new LuaValue[] { 
                    CoerceJavaToLua.coerce(player), 
                    CoerceJavaToLua.coerce(this) 
            });

            if (isCancelled) {
                player.performRoomAction(RoomAction.LEAVE_ROOM, true);
            }
        }
    }

    public void loadMapData(Player player) {

        player.send(new HeightMapMessageComposer(this.getModel()));
        player.send(new FloorMapMessageComposer(this));

        this.send(new UserDisplayMessageComposer(player));
        this.send(new UserStatusMessageComposer(player));

        if (!this.entityManager.getEntities().contains(player)) {
            this.entityManager.getEntities().add(player);
            this.data.updateUsersNow();
        }

        player.send(new UserDisplayMessageComposer(this.entityManager.getEntities()));
        player.send(new UserStatusMessageComposer(this.entityManager.getEntities()));

        for (Player players : this.entityManager.getPlayers()) {
            if (players.getRoomUser().isDancing()) {
                player.send(new DanceMessageComposer(players.getRoomUser().getVirtualId(), players.getRoomUser().getDanceId()));
            }

            if (players.getRoomUser().getCarryItem() > 0) {
                player.send(new CarryObjectComposer(players.getRoomUser().getVirtualId(), players.getRoomUser().getCarryItem())); 
            }
        }

        if (player.hasPermission("room_all_rights") || this.data.getOwnerId() == player.getDetails().getId()) {
            player.getRoomUser().setStatus(EntityStatus.FLAT_CONTROL, "4");
        } else if (this.hasRights(player, false)) {
            player.getRoomUser().setStatus(EntityStatus.FLAT_CONTROL, "1");
        }        

        player.send(new WallOptionsMessageComposer(this.data.hasHiddenWall(), this.data.getWallThickness(), this.data.getFloorThickness()));
        player.send(new RoomPromotionMessageComposer(this));

        this.itemManager.fixFlashingTeleporters();

        player.send(new FloorItemsMessageComposer(this.itemManager.getFloorItems()));
        player.send(new WallItemsMessageComposer(this.itemManager.getWallItems()));
        player.send(new RoomDataMessageComposer(this, player, true, false));

        player.getMessenger().sendStatus(false);

        boolean isCancelled = PluginManager.callEvent(PluginEvent.ROOM_ENTER_EVENT, new LuaValue[] { CoerceJavaToLua.coerce(player), CoerceJavaToLua.coerce(this) });

        if (isCancelled) {
            player.performRoomAction(RoomAction.LEAVE_ROOM, true);
        }

        Group group = this.getGroup();

        if (group != null) {
            if (player.getRoomUser().getMetadata().getAsBool("showGroupHomeroomDialog")) {
                player.getRoomUser().getMetadata().set("showGroupHomeroomDialog", false);
                
                this.send(new NewGroupMessageComposer(this.data.getId(), this.data.getGroupId()));
            }
        }
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

    public void send(MessageComposer response, boolean checkRights) {

        for (Player player : this.getEntityManager().getPlayers()) {

            if (checkRights && this.hasRights(player, false)) {
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
    
    public void dispose(boolean forceDisposal) {

        if (forceDisposal) {

            this.cleanupRoomData();
            RoomManager.removeRoom(this.data.getId());

        } else {

            if (this.entityManager.getPlayers().size() > 0) {
                return;
            }

            this.cleanupRoomData();

            if (PlayerManager.getById(this.data.getOwnerId()) == null && this.data.getRoomType() == RoomType.PRIVATE) { 
                RoomManager.removeRoom(this.data.getId());
            }
        }
    }

    private void cleanupRoomData() {

        if (this.scheduler != null) {
            this.scheduler.cancelTasks();
        }
        
        this.entityManager.cleanupEntities();

        if (this.rights != null) {
            this.rights.clear();
        }
        
        this.scheduler  = null;
        this.mapping = null;
        this.promotion = null;

        this.privateId.set(-1);
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

    public AtomicInteger getPrivateId() {
        return privateId;
    }
}
