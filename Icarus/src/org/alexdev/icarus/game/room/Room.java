package org.alexdev.icarus.game.room;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ScheduledFuture;
import java.util.stream.Collectors;

import org.alexdev.icarus.dao.mysql.ItemDao;
import org.alexdev.icarus.dao.mysql.RoomDao;
import org.alexdev.icarus.game.entity.EntityType;
import org.alexdev.icarus.game.entity.Entity;
import org.alexdev.icarus.game.furniture.interactions.InteractionType;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.ItemType;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.player.PlayerManager;
import org.alexdev.icarus.game.room.model.Position;
import org.alexdev.icarus.game.room.model.RoomModel;
import org.alexdev.icarus.game.room.settings.RoomType;
import org.alexdev.icarus.messages.outgoing.room.HasOwnerRightsMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.PrepareRoomMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.RoomModelMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.RoomRatingMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.RoomRightsLevelMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.RoomSpacesMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.user.HotelViewMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.user.RemoveUserMessageComposer;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;

import com.google.common.collect.Lists;

public class Room {

    private int privateId = -1;
    private boolean disposed;

    private RoomData data;

    private List<Entity> entities; 
    private List<Item> items;
    
    private ScheduledFuture<?> tickTask;
   
    private RoomScheduler scheduler;
    private RoomMapping mapping;


    public Room() {
        this.data = new RoomData(this);
        this.mapping = new RoomMapping(this);
        
        this.entities = new ArrayList<Entity>();
    }

    public void loadRoom(Player player) {

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

        if (roomUser.getRoom().hasRights(player, true)) {
            player.send(new RoomRightsLevelMessageComposer(4));
            player.send(new HasOwnerRightsMessageComposer());


        } else if (roomUser.getRoom().hasRights(player, false)) {
            player.send(new RoomRightsLevelMessageComposer(1));

        } else {
            player.send(new RoomRightsLevelMessageComposer(0));
        }

        player.send(new PrepareRoomMessageComposer(this));

        roomUser.setVirtualId(this.getVirtualId());
        
        Position startingPosition = this.getModel().getDoorPosition();
        roomUser.getPosition().setX(startingPosition.getX());
        roomUser.getPosition().setY(startingPosition.getY());
        roomUser.getPosition().setZ(this.getModel().getHeight(roomUser.getPosition().getX(), roomUser.getPosition().getY()));
        roomUser.setRotation(this.getModel().getDoorRot(), false);
    }

    public void firstEntry() {
        
        this.disposed = false;
 
        if (this.getUsers().size() != 0) {
            return;
        }

        this.items = ItemDao.getRoomItems(this.getData().getId());
        
        this.scheduler = new RoomScheduler(this);
        this.scheduler.scheduleTasks();
    }


    public void leaveRoom(Player player, boolean hotelView) {

        if (hotelView) {;
        player.send(new HotelViewMessageComposer());
        }

        this.send(new RemoveUserMessageComposer(player.getRoomUser().getVirtualId()));

        RoomUser roomUser = player.getRoomUser();
        roomUser.dispose();

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

        if (this.entities != null) {
            this.entities.clear();
        }
        
        this.privateId = -1;
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
    
    public Item[] getFloorItems() {
        List<Item> floorItems = items.stream().filter(item -> item.getType() == ItemType.FLOOR).collect(Collectors.toList());
        return floorItems.toArray(new Item[floorItems.size()]);
    }

    public Item[] getWallItems() {
        List<Item> wallItems = items.stream().filter(item -> item.getType() == ItemType.WALL).collect(Collectors.toList());
        return wallItems.toArray(new Item[wallItems.size()]);
    }
    
    public List<Item> getItems() {
        return this.items;
    }  
    
    public List<Item> getItems(InteractionType interactionType) {
        try {
            return items.stream().filter(item -> item.getData().getInteractionType() == interactionType).collect(Collectors.toList());
        } catch (Exception e) {
            return Lists.newArrayList();
        }
    }

    public List<Entity> getEntities() {
        return entities;
    }

    public RoomData getData() {
        return data;
    }

    public RoomModel getModel() {
        return RoomDao.getModel(this.data.getModel());
    }
    
    public RoomMapping getMapping() {
        return mapping;
    }

    public void save() {
        RoomDao.updateRoom(this);
    }
    public int getVirtualId() {
        this.privateId = this.privateId + 1;
        return this.privateId;
    }

    public boolean isDisposed() {
        return disposed;
    }

}
