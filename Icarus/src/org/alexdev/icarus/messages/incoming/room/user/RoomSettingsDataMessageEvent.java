package org.alexdev.icarus.messages.incoming.room.user;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.outgoing.room.RoomSettingsDataMessageComposer;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class RoomSettingsDataMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage request) {
        
        int roomID = request.readInt();
        
        Room room = player.getRoomUser().getRoom();
        
        if (room.getData().getID() != roomID) {
            return;
        }
        
        player.send(new RoomSettingsDataMessageComposer(room.getData()));
    }
}
