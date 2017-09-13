package org.alexdev.icarus.game.util;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.plugins.PluginEvent;
import org.alexdev.icarus.game.plugins.PluginManager;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.RoomAction;
import org.alexdev.icarus.game.room.settings.RoomState;
import org.alexdev.icarus.game.room.user.RoomUser;
import org.alexdev.icarus.messages.outgoing.room.OwnerRightsMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.RightsLevelMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.RoomModelMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.RoomOwnerRightsComposer;
import org.alexdev.icarus.messages.outgoing.room.RoomRatingMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.RoomSpacesMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.notify.GenericDoorbellMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.notify.GenericErrorMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.notify.GenericNoAnswerDoorbellMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.notify.RoomEnterErrorMessageComposer;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

public class RoomUtil {

    public static void entityRoomEntry(Player player, Room room, String password) {
        
        entityRoomEntry(player, room, password, 
                room.getModel().getDoorLocation().getX(), 
                room.getModel().getDoorLocation().getY(), 
                room.getModel().getDoorLocation().getRotation());
    }
    
    public static void entityRoomEntry(Player player, Room room, String password, int x, int y, int rotation) {
        
        if (player.inRoom()) {
            player.performRoomAction(RoomAction.LEAVE_ROOM, false);
        }

        boolean isOwner = room.hasRights(player, true);

        if (room.getData().getUsersNow() >= room.getData().getUsersMax()) {
            if (!player.hasPermission("user_enter_full_rooms")) {
                if (player.getDetails().getId() != room.getData().getOwnerId()) {
                    player.send(new RoomEnterErrorMessageComposer(1));
                    return;
                }
            }
        }

        if (player.getRoomUser().isTeleporting()) {
            if (player.getRoomUser().getTeleportRoomId() != room.getData().getId()) {
                player.performRoomAction(RoomAction.LEAVE_ROOM, true);
            } else {
                player.getRoomUser().setTeleporting(false);
                player.getRoomUser().setTeleportRoomId(0);
            }
        }
        else {

            if (room.getData().getState().getStateCode() > 0 && !room.hasRights(player, false)) {
                if (room.getData().getState() == RoomState.DOORBELL) {

                    if (room.getEntityManager().getPlayers().size() > 0) {
                        player.send(new GenericDoorbellMessageComposer(1));
                        room.send(new GenericDoorbellMessageComposer(player.getDetails().getName()), true);
                    } else {
                        player.send(new GenericNoAnswerDoorbellMessageComposer());
                    }

                    return;
                }

                if (room.getData().getState() == RoomState.PASSWORD) {
                    if (!password.equals(room.getData().getPassword())) {
                        player.send(new GenericErrorMessageComposer(-100002));
                        return;
                    }
                }

                if (room.getData().getState() == RoomState.INVISIBLE) {
                    player.performRoomAction(RoomAction.LEAVE_ROOM, true);
                    return;
                }
            }
        }

        RoomUser roomUser = player.getRoomUser();

        roomUser.setRoom(room);
        roomUser.getStatuses().clear();

        player.send(new RoomModelMessageComposer(room.getModel().getName(), room.getData().getId()));
        player.send(new RoomRatingMessageComposer(room.getData().getScore()));

        int floorData = Integer.parseInt(room.getData().getFloor());
        int wallData = Integer.parseInt(room.getData().getWall());

        if (floorData > 0) {
            player.send(new RoomSpacesMessageComposer("floor", room.getData().getFloor()));
        }

        if (wallData > 0) {
            player.send(new RoomSpacesMessageComposer("wallpaper", room.getData().getWall()));
        }

        player.send(new RoomSpacesMessageComposer("landscape", room.getData().getLandscape()));
        player.send(new RoomOwnerRightsComposer(room.getData().getId(), isOwner));

        if (roomUser.getRoom().hasRights(player, true)) {
            player.send(new RightsLevelMessageComposer(4));
            player.send(new OwnerRightsMessageComposer());

        } else if (roomUser.getRoom().hasRights(player, false)) {
            player.send(new RightsLevelMessageComposer(1));

        } else {
            player.send(new RightsLevelMessageComposer(0));
        }

        roomUser.setVirtualId(room.getPrivateId().incrementAndGet());
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
