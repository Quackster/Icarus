package org.alexdev.icarus.messages.incoming.room;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.enums.RoomAction;
import org.alexdev.icarus.log.Log;
import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class LeaveRoomMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage request) {
       
        Log.info("LEAVE ROOM EVENT CALLED!");
        
        player.performRoomAction(RoomAction.LEAVE_ROOM, true);
    }
}
