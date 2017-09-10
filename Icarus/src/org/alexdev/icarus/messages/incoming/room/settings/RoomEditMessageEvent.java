package org.alexdev.icarus.messages.incoming.room.settings;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.outgoing.room.RoomEditMessageComposer;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class RoomEditMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage request) {
        
        int roomId = request.readInt();
        
        Room room = player.getRoomUser().getRoom();
        
        if (room.getData().getId() != roomId) {
            return;
        }
        
        player.send(new RoomEditMessageComposer(room.getData()));
    }
}
