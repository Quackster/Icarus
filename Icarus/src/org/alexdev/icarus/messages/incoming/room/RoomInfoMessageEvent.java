package org.alexdev.icarus.messages.incoming.room;

import org.alexdev.icarus.dao.mysql.room.RoomDao;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.outgoing.room.RoomDataMessageComposer;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class RoomInfoMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage request) {

        Room room = RoomDao.getRoom(request.readInt(), true);

        if (room == null) {
            return;
        }
        
        int isLoading = request.readInt();
        int checkEntry = request.readInt();
        
        player.send(new RoomDataMessageComposer(room, player, isLoading == 1, checkEntry == 1));
    }
}
