package org.alexdev.icarus.game.util;

import org.alexdev.icarus.game.entity.EntityStatus;
import org.alexdev.icarus.game.groups.Group;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.plugins.PluginEvent;
import org.alexdev.icarus.game.plugins.PluginManager;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.enums.RoomAction;
import org.alexdev.icarus.game.room.user.RoomUser;
import org.alexdev.icarus.messages.incoming.room.RoomPromotionMessageComposer;
import org.alexdev.icarus.messages.outgoing.groups.NewGroupMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.FloorMapMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.HeightMapMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.OwnerRightsMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.RightsLevelMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.RoomModelMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.RoomOwnerRightsComposer;
import org.alexdev.icarus.messages.outgoing.room.RoomRatingMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.RoomSpacesMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.WallOptionsMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.items.FloorItemsMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.items.WallItemsMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.user.CarryObjectComposer;
import org.alexdev.icarus.messages.outgoing.room.user.DanceMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.user.UserDisplayMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.user.UserStatusMessageComposer;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

public class RoomUtil {

    public static void playerRoomEntry(Player player, Room room, String password) {
        
        playerRoomEntry(player, room, 
                room.getModel().getDoorLocation().getX(), 
                room.getModel().getDoorLocation().getY(), 
                room.getModel().getDoorLocation().getRotation());
    }
    
    public static void playerRoomEntry(Player player, Room room, int x, int y, int rotation) {
        
        if (player.inRoom()) {
            player.performRoomAction(RoomAction.LEAVE_ROOM, false);
        }

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
        
        boolean isOwner = roomUser.getRoom().hasRights(player.getDetails().getId(), true);
 
        player.send(new RoomSpacesMessageComposer("landscape", room.getData().getLandscape()));
        player.send(new RoomOwnerRightsComposer(room.getData().getId(), isOwner));
        
        if (isOwner) {
            player.send(new RightsLevelMessageComposer(4));
            player.send(new OwnerRightsMessageComposer());

        } else if (roomUser.getRoom().hasRights(player.getDetails().getId(), false)) {
            player.send(new RightsLevelMessageComposer(1));

        } else {
            player.send(new RightsLevelMessageComposer(0));
        }
        
        boolean firstUserEntry = !(room.getEntityManager().getPlayers().size() > 0);
        
        if (firstUserEntry) {

            room.getItemManager().refreshRoomFurniture();
            room.getMapping().regenerateCollisionMaps();
            room.getScheduler().scheduleTasks();
            room.getEntityManager().addPets();

            boolean isCancelled = PluginManager.callEvent(PluginEvent.ROOM_FIRST_ENTRY_EVENT, new LuaValue[] { 
                    CoerceJavaToLua.coerce(player), 
                    CoerceJavaToLua.coerce(room) 
            });

            if (isCancelled) {
                player.performRoomAction(RoomAction.LEAVE_ROOM, true);
            }
        }
        
        room.getEntityManager().addEntity(player, x, y, rotation);
    }

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
            if (players.getRoomUser().isDancing()) {
                player.send(new DanceMessageComposer(players.getRoomUser().getVirtualId(), players.getRoomUser().getDanceId()));
            }

            if (players.getRoomUser().getCarryItem() > 0) {
                player.send(new CarryObjectComposer(players.getRoomUser().getVirtualId(), players.getRoomUser().getCarryItem())); 
            }
        }

        if (player.getDetails().hasPermission("room_all_rights") || room.getData().getOwnerId() == player.getDetails().getId()) {
            player.getRoomUser().setStatus(EntityStatus.FLAT_CONTROL, "4");
        } else if (room.hasRights(player.getDetails().getId(), false)) {
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
}
