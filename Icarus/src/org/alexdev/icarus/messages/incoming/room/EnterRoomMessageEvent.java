package org.alexdev.icarus.messages.incoming.room;

import org.alexdev.icarus.dao.mysql.room.RoomDao;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.plugins.PluginEvent;
import org.alexdev.icarus.game.plugins.PluginManager;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.enums.RoomAction;
import org.alexdev.icarus.game.room.enums.RoomState;
import org.alexdev.icarus.game.util.RoomUtil;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.outgoing.room.notify.GenericDoorbellMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.notify.GenericErrorMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.notify.GenericNoAnswerDoorbellMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.notify.RoomEnterErrorMessageComposer;
import org.alexdev.icarus.server.api.messages.ClientMessage;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;

public class EnterRoomMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage request) {

        int roomId = request.readInt();
        
        Room room = RoomDao.getRoom(roomId, true);
                
        if (room == null) {
            return;
        }
        
        player.getRoomUser().setRequestedRoomId(roomId);
        
        boolean isCancelled = PluginManager.callEvent(PluginEvent.ROOM_REQUEST_ENTER_EVENT, new LuaValue[] { 
                CoerceJavaToLua.coerce(player), 
                CoerceJavaToLua.coerce(room) 
        });
        
        if (isCancelled) {
            player.performRoomAction(RoomAction.LEAVE_ROOM, true);
            return;
        }
        
        if (player.getRoomUser().getRequestedRoomId() != roomId) {
            room.dispose(false);
            room = RoomDao.getRoom(player.getRoomUser().getRequestedRoomId(), true);
        }

        if (room == null) {
            return;
        }
        
        String password = request.readString();
        
        if (room.getData().getUsersNow() >= room.getData().getUsersMax()) {
            if (!player.getDetails().hasPermission("user_enter_full_rooms")) {
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

            if (room.getData().getState().getStateCode() > 0 && !room.hasRights(player.getDetails().getId(), false)) {
                if (room.getData().getState() == RoomState.DOORBELL) {

                    if (room.getEntityManager().getPlayers().size() > 0) {
                        player.send(new GenericDoorbellMessageComposer(1));
                        room.sendWithRights(new GenericDoorbellMessageComposer(player.getDetails().getName()));
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
        
        RoomUtil.playerRoomEntry(player, room, password);
    }
}
