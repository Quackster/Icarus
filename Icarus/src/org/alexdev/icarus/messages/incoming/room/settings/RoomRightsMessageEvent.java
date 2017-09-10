package org.alexdev.icarus.messages.incoming.room.settings;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.outgoing.room.settings.RoomRightsListComposer;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class RoomRightsMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {
       
        Room room = player.getRoomUser().getRoom();

        if (room == null) {
            return;
        }
        
        if (!room.hasRights(player, true)) {
            return;
        }
        
        int roomId = reader.readInt();
        
        player.send(new RoomRightsListComposer(roomId, room.getRights()));
    }
}
