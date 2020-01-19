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
import org.alexdev.icarus.messages.outgoing.groups.GroupPurchasedMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.*;
import org.alexdev.icarus.messages.outgoing.room.items.*;
import org.alexdev.icarus.messages.outgoing.room.settings.RoomScoreMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.settings.YouAreControllerMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.settings.YouAreNotControllerMessageComposer;
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

        if (player.inRoom()) {
            player.performRoomAction(RoomAction.LEAVE_ROOM, false);
        }

        roomUser.setRoom(room);
        roomUser.getStatuses().clear();

        player.send(new RoomEnterComposer());
        player.send(new RoomModelMessageComposer(room.getModel().getName(), room.getData().getId()));

        int floorData = Integer.valueOf(room.getData().getFloor());
        int wallData = Integer.valueOf(room.getData().getWall());

        if (floorData > 0) {
            player.send(new RoomSpacesMessageComposer("floor", room.getData().getFloor()));
        }

        if (wallData > 0) {
            player.send(new RoomSpacesMessageComposer("wallpaper", room.getData().getWall()));
        }

        player.send(new RoomSpacesMessageComposer("landscape", room.getData().getLandscape()));
        player.send(new RoomScoreMessageComposer(room));

        room.getEntityManager().addEntity(player, x, y, rotation);
    }

    /**
     * Refresh the rights of this room user
     *
     * @param room the room to refresh in
     * @param player the user to refresh for
     */
    public static void refreshRights(Room room, Player player) {
        boolean isOwner = (room.hasOwnership(player.getEntityId()) || player.hasPermission("room_all_rights"));

        if (isOwner) {
            player.send(new OwnerRightsMessageComposer());
            player.send(new YouAreControllerMessageComposer(4));
            player.getRoomUser().setStatus(EntityStatus.FLAT_CONTROL, "useradmin");
        } else if (room.hasGroupRights(player.getEntityId(), true)) {
            player.send(new YouAreControllerMessageComposer(3));
            player.getRoomUser().setStatus(EntityStatus.FLAT_CONTROL, "3");
        } else if (room.hasGroupRights(player.getEntityId(), false)|| room.hasRights(player.getEntityId(), false)) {
            player.send(new YouAreControllerMessageComposer(1));
            player.getRoomUser().setStatus(EntityStatus.FLAT_CONTROL, "1");
        } else {
            player.send(new YouAreNotControllerMessageComposer());
            player.getRoomUser().removeStatus(EntityStatus.FLAT_CONTROL);
        }
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

        player.send(new HeightMapMessageComposer(room));
        player.send(new FloorMapMessageComposer(room));

        for (Player players : room.getEntityManager().getPlayers()) {
            if (players.getRoomUser().getDanceId() > 0) {
                player.send(new DanceMessageComposer(players.getRoomUser().getVirtualId(), players.getRoomUser().getDanceId()));
            }

            if (players.getRoomUser().getEffectId() > 0) {
                player.send(new DisplayEffectMessageComposer(players.getRoomUser().getVirtualId(), players.getRoomUser().getVirtualId()));
            }
            
            if (players.getRoomUser().getCarryItem() > 0) {
                player.send(new CarryObjectMessageComposer(players.getRoomUser().getVirtualId(), players.getRoomUser().getCarryItem()));
            }
        }

        if (player.hasPermission("room_all_rights") || room.getData().getOwnerId() == player.getEntityId()) {
            player.getRoomUser().setStatus(EntityStatus.FLAT_CONTROL, "3");
        } else if (room.hasRights(player.getEntityId())) {
            player.getRoomUser().setStatus(EntityStatus.FLAT_CONTROL, "1");
        }        

        player.send(new WallOptionsMessageComposer(room.getData().hasHiddenWall(), room.getData().getWallThickness(), room.getData().getFloorThickness()));
        player.send(new RoomDataMessageComposer(room, player, false, true));
        //player.send(new RoomPromotionMessageComposer(room));

        player.send(new FloorItemsMessageComposer(room.getItemManager().getFloorItems()));

        boolean isOwner = (player.getRoomUser().getRoom().hasOwnership(player.getEntityId())
                || player.hasPermission("room_all_rights"));

        refreshRights(room, player);
        player.send(new RoomOwnerRightsComposer(room.getData().getId(), isOwner));
        //player.send(new WallItemsMessageComposer(room.getItemManager().getWallItems()));

        player.getMessenger().sendStatus(false);

        boolean isCancelled = PluginManager.getInstance().callEvent(PluginEvent.ROOM_ENTER_EVENT, new LuaValue[] {
                CoerceJavaToLua.coerce(player), 
                CoerceJavaToLua.coerce(room) 
        });

        if (isCancelled) {
            player.performRoomAction(RoomAction.LEAVE_ROOM, true);
        }

        Group group = room.getGroup();

        if (group != null) {
            if (player.getMetadata().getBoolean("showGroupHomeroomDialog")) {
                player.getMetadata().set("showGroupHomeroomDialog", false);
                player.send(new GroupPurchasedMessageComposer(room.getData().getId(), room.getData().getGroupId()));
            }
        }

        if (player.getRoomUser().getEffectId() > 0) { // Does this user have an effect enabled?
            // If so, show everybody on room entry!
            player.getRoomUser().applyEffect(player.getRoomUser().getEffectId());
        }

        player.send(new UserDisplayMessageComposer(room.getEntityManager().getEntities()));
        player.send(new UserStatusMessageComposer(room.getEntityManager().getEntities()));
    }
    
    /**
     * Serialise room information.
     *  @param room the room
     * @param response the response
     */
    public static void serialise(Room room, Response response) {
        RoomData data = room.getData();

        response.writeInt(data.getId());
        response.writeString(data.getName());
        response.writeInt(data.getOwnerId());
        response.writeString(data.getOwnerName());
        response.writeInt(data.getState().getStateCode());
        response.writeInt(data.getUsersNow());
        response.writeInt(data.getUsersMax());
        response.writeString(data.getDescription());
        response.writeInt(data.getTradeState());
        response.writeInt(2);
        response.writeInt(data.getScore());
        response.writeInt(data.getCategory());
        response.writeInt(data.getTags().length);

        for (String tag : data.getTags()) {
            response.writeString(tag);
        }
        
        AtomicInteger roomListingType = new AtomicInteger(0);
        
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
        
        if (room.hasPromotion()) {
            roomListingType.getAndAdd(4);
        }
        
        if (room.hasGroup()) {
            roomListingType.getAndAdd(2);
        }

        response.writeInt(roomListingType.get());
        
        if (data.getThumbnail() != null) {
            if (data.getThumbnail().length() > 0) {
                response.writeString(data.getThumbnail());
            }
        }
        
        if (room.hasGroup()) {
            Group group = room.getGroup();
            response.writeInt(group.getId());
            response.writeString(group.getTitle());
            response.writeString(group.getBadge());
        }
        
        if (room.hasPromotion()) {
            response.writeString(room.getPromotion().getPromotionName());
            response.writeString(room.getPromotion().getPromotionDescription());
            response.writeInt(room.getPromotion().getPromotionMinutesLeft().get());
        }
    }
}
