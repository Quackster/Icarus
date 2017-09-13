package org.alexdev.icarus.messages.incoming.room;

import org.alexdev.icarus.dao.mysql.room.RoomDao;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.plugins.PluginEvent;
import org.alexdev.icarus.game.plugins.PluginManager;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.messages.MessageEvent;
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
        
        boolean isCancelled = PluginManager.callEvent(PluginEvent.ROOM_REQUEST_ENTER_EVENT, new LuaValue[] { CoerceJavaToLua.coerce(player), CoerceJavaToLua.coerce(room) });
        
        if (isCancelled) {
            player.leaveRoom(true);
            return;
        }
        
        // If the room is not the one we wanted, dispose it.
        if (player.getRoomUser().getRequestedRoomId() != roomId) {
            
            // dispose old room, NOT forced disposal
            room.dispose(false);
            
            // load new room
            room = RoomDao.getRoom(player.getRoomUser().getRequestedRoomId(), true);
            
        }

        if (room == null) {
            return;
        }
        
        room.loadRoom(player, request.readString());
    }

}
