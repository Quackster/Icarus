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

        int roomID = request.readInt();
        
        Room room = RoomDao.getRoom(roomID, true);
                
        if (room == null) {
            return;
        }
        
        player.getRoomUser().setRequestedRoomID(roomID);
        
        boolean isCancelled = PluginManager.callEvent(PluginEvent.ROOM_REQUEST_ENTER_EVENT, new LuaValue[] { CoerceJavaToLua.coerce(player), CoerceJavaToLua.coerce(room) });
        
        if (isCancelled) {
            room.leaveRoom(player, true);
            return;
        }
        
        // If the room is not the one we wanted, dispose it.
        if (player.getRoomUser().getRequestedRoomID() != roomID) {
            
            // dispose old room, NOT forced disposal
            room.dispose(false);
            
            // load new room
            room = RoomDao.getRoom(player.getRoomUser().getRequestedRoomID(), true);
            
        }

        if (room == null) {
            return;
        }
        
        room.loadRoom(player, request.readString());
    }

}
