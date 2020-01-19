package org.alexdev.icarus.messages.incoming.room.settings;

import org.alexdev.icarus.dao.mysql.room.RoomDao;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.messages.outgoing.room.RoomDataMessageComposer;
import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class RoomInfoMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage request) {
        Room room = RoomDao.getRoom(request.readInt(), true);

        int isLoading = request.readInt();
        int checkEntry = request.readInt();

        if (room != null) {
            boolean unknown = true;

            if (isLoading == 0 && checkEntry == 1) {
                unknown = false;
            }

            player.send(new RoomDataMessageComposer(room, player, true, unknown));
        }


        /*

        int something = this.packet.readInt();
        int something2 = this.packet.readInt();

        if (room != null) {
            boolean unknown = true;

            if (something == 0 && something2 == 1) {
                unknown = false;
            }

            //this.client.getHabbo().getHabboInfo().getCurrentRoom() != room
            this.client.sendResponse(new RoomDataComposer(room, this.client.getHabbo(), true, unknown));
        }

         */
    }
}
