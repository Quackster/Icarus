package org.alexdev.icarus.game.util;

import java.util.concurrent.atomic.AtomicInteger;

import org.alexdev.icarus.game.entity.EntityStatus;
import org.alexdev.icarus.game.groups.Group;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.plugins.PluginEvent;
import org.alexdev.icarus.game.plugins.PluginManager;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.RoomData;
import org.alexdev.icarus.game.room.enums.RoomAction;
import org.alexdev.icarus.game.room.enums.RoomType;
import org.alexdev.icarus.game.room.user.RoomUser;
import org.alexdev.icarus.messages.incoming.room.RoomPromotionMessageComposer;
import org.alexdev.icarus.messages.outgoing.groups.NewGroupMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.*;
import org.alexdev.icarus.messages.outgoing.room.items.*;
import org.alexdev.icarus.messages.outgoing.room.user.*;
import org.alexdev.icarus.messages.outgoing.user.effects.*;
import org.alexdev.icarus.server.api.messages.Response;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

public class RoomUtil {

    /**
     * Player room entry, this method without specifying
     * coordinates will default to the default room model
     * coords.
     *
     * @param player the player
     * @param room the room
     * @param password the password
     */
    public static void playerRoomEntry(Player player, Room room, String password) {
        
        playerRoomEntry(player, room, 
                room.getModel().getDoorLocation().getX(), 
                room.getModel().getDoorLocation().getY(), 
                room.getModel().getDoorLocation().getRotation());
    }
    
    /**
     * Player room entry.
     *
     * @param player the player
     * @param room the room
     * @param x the x
     * @param y the y
     * @param rotation the rotation
     */
    public static void playerRoomEntry(Player player, Room room, int x, int y, int rotation) {
        
        RoomUser roomUser = player.getRoomUser();

        roomUser.setRoom(room);
        roomUser.getStatuses().clear();

        player.send(new RoomModelMessageComposer(room.getModel().getName(), room.getData().getId()));
        player.send(new RoomRatingMessageComposer(room.getData().getScore()));

        int floorData = Integer.valueOf(room.getData().getFloor());
        int wallData = Integer.valueOf(room.getData().getWall());

        if (floorData > 0) {
            player.send(new RoomSpacesMessageComposer("floor", room.getData().getFloor()));
        }

        if (wallData > 0) {
            player.send(new RoomSpacesMessageComposer("wallpaper", room.getData().getWall()));
        }
        
        boolean isOwner = (roomUser.getRoom().hasOwnership(player.getEntityId()) 
                || player.getDetails().hasPermission("room_all_rights"));
 
        player.send(new RoomSpacesMessageComposer("landscape", room.getData().getLandscape()));
        player.send(new RoomOwnerRightsComposer(room.getData().getId(), isOwner));
        
        if (isOwner) {
            player.send(new RightsLevelMessageComposer(4));
            player.send(new OwnerRightsMessageComposer());

        } else if (room.hasRights(player.getEntityId())) {
            player.send(new RightsLevelMessageComposer(1));

        } else {
            player.send(new RightsLevelMessageComposer(0));
        }
        
        room.getEntityManager().addEntity(player, x, y, rotation);
    }

    /**
     * Player room map entry.
     *
     * @param player the player
     * @param room the room
     */
    public static void playerRoomMapEntry(Player player, Room room) {

        if (room == null) {
            return;
        }
        
        // Do NOT continue if the user is already in room
        if (!room.getEntityManager().getEntities().contains(player)) {
            return;
        }
        
        player.send(new HeightMapMessageComposer(room.getModel()));
        player.send(new FloorMapMessageComposer(room));

        player.send(new UserDisplayMessageComposer(room.getEntityManager().getEntities()));
        player.send(new UserStatusMessageComposer(room.getEntityManager().getEntities()));

        for (Player players : room.getEntityManager().getPlayers()) {
            if (players.getRoomUser().getDanceId() > 0) {
                player.send(new DanceMessageComposer(players.getRoomUser().getVirtualId(), players.getRoomUser().getDanceId()));
            }

            if (players.getRoomUser().getEffectId() > 0) {
                player.send(new EffectMessageComposer(players.getRoomUser().getVirtualId(), players.getRoomUser().getVirtualId()));
            }
            
            if (players.getRoomUser().getCarryItem() > 0) {
                player.send(new CarryObjectComposer(players.getRoomUser().getVirtualId(), players.getRoomUser().getCarryItem())); 
            }
        }

        if (player.getDetails().hasPermission("room_all_rights") || room.getData().getOwnerId() == player.getEntityId()) {
            player.getRoomUser().setStatus(EntityStatus.FLAT_CONTROL, "4");
        } else if (room.hasRights(player.getEntityId())) {
            player.getRoomUser().setStatus(EntityStatus.FLAT_CONTROL, "1");
        }        

        player.send(new WallOptionsMessageComposer(room.getData().hasHiddenWall(), room.getData().getWallThickness(), room.getData().getFloorThickness()));
        player.send(new RoomPromotionMessageComposer(room));
        player.send(new FloorItemsMessageComposer(room.getItemManager().getFloorItems()));
        player.send(new WallItemsMessageComposer(room.getItemManager().getWallItems()));

        player.getMessenger().sendStatus(false);

        boolean isCancelled = PluginManager.callEvent(PluginEvent.ROOM_ENTER_EVENT, new LuaValue[] { 
                CoerceJavaToLua.coerce(player), 
                CoerceJavaToLua.coerce(room) 
        });

        if (isCancelled) {
            player.performRoomAction(RoomAction.LEAVE_ROOM, true);
        }

        Group group = room.getGroup();

        if (group != null) {
            if (player.getRoomUser().getMetadata().getBoolean("showGroupHomeroomDialog")) {
                player.getRoomUser().getMetadata().set("showGroupHomeroomDialog", false);
                player.send(new NewGroupMessageComposer(room.getData().getId(), room.getData().getGroupId()));
            }
        }
    }
    
    /**
     * Serialise room information.
     *
     * @param room the room
     * @param response the response
     * @param enterRoom the enter room
     */
    public static void serialise(Room room, Response response, boolean enterRoom) {
        
        RoomData data = room.getData();
        Group group = room.getGroup();
        
        response.writeInt(data.getId());
        response.writeString(data.getName());
        response.writeInt(data.getOwnerId());
        response.writeString(data.getOwnerName());
        response.writeInt(data.getState().getStateCode());
        response.writeInt(data.getUsersNow());
        response.writeInt(data.getUsersMax());
        response.writeString(data.getDescription());
        response.writeInt(data.getTradeState());
        response.writeInt(data.getScore());
        response.writeInt(0);
        response.writeInt(data.getCategory());
        response.writeInt(data.getTags().length);

        for (String tag : data.getTags()) {
            response.writeString(tag);
        }
        
        AtomicInteger roomListingType = new AtomicInteger(enterRoom ? 32 : 0);
        
        if (data.getThumbnail() != null) {
            if (data.getThumbnail().length() > 0) {
                roomListingType.getAndAdd(1);
            }
        }
        
        if (data.getRoomType() == RoomType.PRIVATE) {
            roomListingType.getAndAdd(8);
        }

        if (data.isAllowPets()) { 
            roomListingType.getAndAdd(16);
        }
        
        if (room.getPromotion() != null) {
            roomListingType.getAndAdd(4);
        }
        
        if (group != null) {
            roomListingType.getAndAdd(2);
        }

        response.writeInt(roomListingType.get());
        
        if (data.getThumbnail() != null) {
            if (data.getThumbnail().length() > 0) {
                response.writeString(data.getThumbnail());
            }
        }
        
        if (group != null) {
            response.writeInt(group.getId());
            response.writeString(group.getTitle());
            response.writeString(group.getBadge());
        }
        
        if (room.getPromotion() != null) {
            response.writeString(room.getPromotion().getPromotionName());
            response.writeString(room.getPromotion().getPromotionDescription());
            response.writeInt(room.getPromotion().getPromotionMinutesLeft().get());
        }
    }
}
