package org.alexdev.icarus.game.util;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.plugins.PluginEvent;
import org.alexdev.icarus.game.plugins.PluginManager;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.RoomAction;
import org.alexdev.icarus.game.room.user.RoomUser;
import org.alexdev.icarus.messages.outgoing.room.OwnerRightsMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.RightsLevelMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.RoomModelMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.RoomOwnerRightsComposer;
import org.alexdev.icarus.messages.outgoing.room.RoomRatingMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.RoomSpacesMessageComposer;
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

        player.send(new RoomSpacesMessageComposer("landscape", room.getData().getLandscape()));
        player.send(new RoomOwnerRightsComposer(room.getData().getId(), room.hasRights(player, true)));

        if (roomUser.getRoom().hasRights(player, true)) {
            player.send(new RightsLevelMessageComposer(4));
            player.send(new OwnerRightsMessageComposer());

        } else if (roomUser.getRoom().hasRights(player, false)) {
            player.send(new RightsLevelMessageComposer(1));

        } else {
            player.send(new RightsLevelMessageComposer(0));
        }

        roomUser.setVirtualId(room.getVirtualTicketCounter().incrementAndGet());
        roomUser.getPosition().setX(x);
        roomUser.getPosition().setY(y);
        roomUser.getPosition().setZ(room.getModel().getHeight(roomUser.getPosition().getX(), roomUser.getPosition().getY()));
        roomUser.getPosition().setRotation(rotation);

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
    }
}
