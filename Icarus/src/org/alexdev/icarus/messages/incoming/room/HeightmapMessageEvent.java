package org.alexdev.icarus.messages.incoming.room;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.log.Log;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class HeightmapMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage request) {

        Room room = player.getRoomUser().getRoom();

        if (room == null) {
            return;
        }
        
        if (room.getEntities().contains(player)) {
            return;
        }
        
        Log.println("CALLED!!! ~~~~~~~~~~~~~~");
        room.loadMapData(player);

    }
}