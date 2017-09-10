package org.alexdev.icarus.messages.incoming.navigator;

import org.alexdev.icarus.dao.mysql.navigator.NavigatorDao;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.outgoing.navigator.CreateRoomMessageComposer;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class CreateRoomMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage request) {
        
        String name = request.readString();
        String description = request.readString();
        String model = request.readString();
        int category = request.readInt();
        int usersMax = request.readInt();
        int tradeState = request.readInt();
        
        if (name == null || description == null || model == null) {
            return;
        }
        
        Room room = NavigatorDao.createRoom(player, name, description, model, category, usersMax, tradeState);
        player.send(new CreateRoomMessageComposer(room.getData().getID(), room.getData().getName()));
    }
}
