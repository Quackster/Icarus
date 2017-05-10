package org.alexdev.icarus.game.room;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.alexdev.icarus.Icarus;
import org.alexdev.icarus.dao.mysql.RoomDao;
import org.alexdev.icarus.game.entity.EntityType;
import org.alexdev.icarus.game.entity.IEntity;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.player.PlayerManager;
import org.alexdev.icarus.game.room.player.RoomUser;
import org.alexdev.icarus.game.room.settings.RoomType;
import org.alexdev.icarus.log.Log;
import org.alexdev.icarus.messages.outgoing.room.HasOwnerRightsMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.PrepareRoomMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.RoomModelMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.RoomRatingMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.RoomRightsLevelMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.RoomSpacesMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.user.HotelViewMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.user.RemoveUserMessageComposer;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;

public class Room {

    private int privateId;
    private boolean disposed;

    private RoomData data;
    private RoomItems items;
    private RoomCycle cycle;

    private List<IEntity> entities;
    private ScheduledFuture<?> tickTask;

    public Room() {
        this.data = new RoomData(this);
        this.entities = new ArrayList<IEntity>();
    }

    public void loadRoom(Player player) {

        RoomUser roomUser = player.getRoomUser();

        roomUser.setRoom(this);
        roomUser.setLoadingRoom(true);
        roomUser.getStatuses().clear();

        player.send(new RoomModelMessageComposer(this.getData().getModel().getName(), this.getData().getId()));
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

        if (roomUser.getRoom().hasRights(player, true)) {
            player.send(new RoomRightsLevelMessageComposer(4));
            player.send(new HasOwnerRightsMessageComposer());


        } else if (roomUser.getRoom().hasRights(player, false)) {
            player.send(new RoomRightsLevelMessageComposer(1));

        } else {
            player.send(new RoomRightsLevelMessageComposer(0));
        }

        player.send(new PrepareRoomMessageComposer(this));
    }

    public void firstEntry() {

        if (this.getUsers().size() != 0) {
            return;
        }

        this.disposed = false;
        this.cycle = new RoomCycle(this);
        this.items = new RoomItems(this);
        this.items.load();
    }


    public void leaveRoom(Player player, boolean hotelView) {

        if (hotelView) {;
        player.send(new HotelViewMessageComposer());
        }

        this.send(new RemoveUserMessageComposer(player.getRoomUser().getVirtualId()));

        RoomUser roomUser = player.getRoomUser();
        roomUser.reset();

        if (this.entities != null) {
            this.entities.remove(player);
        }

        player.getMessenger().sendStatus(false);

        this.dispose(false);
    }

    public boolean hasRights(Player player, boolean ownerCheckOnly) {

        int userID = player.getDetails().getId();

        if (this.data.getOwnerId() == userID) {
            return true;
        } else {
            if (!ownerCheckOnly) {
                return this.data.getRights().contains(Integer.valueOf(userID));
            }
        }

        return false;
    }

    public void dispose(boolean forceDisposal) {

        if (forceDisposal) {

            this.cleanupRoomData();
            this.entities = null;

            RoomManager.getLoadedRooms().remove(this);

        } else {

            if (this.disposed) {
                return;
            }

            if (this.getUsers().size() > 0) {
                return;
            }

            this.cleanupRoomData();

            if (PlayerManager.findById(this.data.getOwnerId()) == null && this.data.getRoomType() == RoomType.PRIVATE) { 
                RoomManager.getLoadedRooms().remove(this);
            }
        }
    }

    private void cleanupRoomData() {

        if (this.tickTask != null) {
            this.tickTask.cancel(true);
            this.tickTask = null;
        }

        if (this.items != null) {
            this.items.dispose();
            this.items = null;
        }

        if (this.entities != null) {
            this.entities.clear();
        }		
    }

    public void send(OutgoingMessageComposer response, boolean checkRights) {

        if (this.disposed) {
            return;
        }

        for (Player player : this.getUsers()) {

            if (checkRights && this.hasRights(player, false)) {
                player.send(response);
            }
        }
    }


    public void send(OutgoingMessageComposer response) {

        if (this.disposed) {
            return;
        }

        for (Player player : this.getUsers()) {
            player.send(response);
        }
    }

    public List<Player> getUsers() {

        List<Player> sessions = new ArrayList<Player>();

        for (IEntity entity : this.getEntities(EntityType.PLAYER)) {
            Player player = (Player)entity;
            sessions.add(player);
        }

        return sessions;
    }

    public List<IEntity> getEntities(EntityType type) {
        List<IEntity> e = new ArrayList<IEntity>();

        for (IEntity entity : this.entities) {
            if (entity.getType() == type) {
                e.add(entity);
            }
        }

        return e;
    }

    public List<IEntity> getEntities() {
        return entities;
    }

    public RoomData getData() {
        return data;
    }

    public void save() {
        RoomDao.updateRoom(this);
    }


    public int getVirtualId() {
        this.privateId = this.privateId + 1;
        return this.privateId;
    }

    public RoomItems getItemManager() {
        return items;
    }

    public RoomCycle getCycle() {
        return cycle;
    }

}
