package org.alexdev.icarus.messages.incoming.room.user;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.RoomUser;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class DanceMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage request) {
        
        RoomUser roomUser = player.getRoomUser();

        if (roomUser == null) {
            return;
        }

        Room room = roomUser.getRoom();

        if (room == null) {
            return;
        }
        
        int danceID = request.readInt();
        
        if (danceID < 0 || danceID > 4)
            danceID = 0;
        
        if (danceID > 0) {
            roomUser.carryItem(0); // remove anything they were carrying
        }

        roomUser.startDancing(danceID);
    }
}
