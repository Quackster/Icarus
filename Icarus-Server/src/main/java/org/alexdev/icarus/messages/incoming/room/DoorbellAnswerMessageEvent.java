package org.alexdev.icarus.messages.incoming.room;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.player.PlayerManager;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.messages.outgoing.room.notify.AcceptUserInsideRoomMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.notify.GenericNoAnswerDoorbellMessageComposer;
import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class DoorbellAnswerMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage request) {
        Room room = player.getRoomUser().getRoom();

        if (room == null) {
            return;
        }

        String name = request.readString();
        boolean accept = request.readBoolean();

        if (name == null) {
            return;
        }

        Player client = PlayerManager.getInstance().getByName(name);

        if (client == null || client.getDetails() == null) {
            return;
        }

        if (!accept) {
            client.send(new GenericNoAnswerDoorbellMessageComposer());
        
        } else {
            client.send(new AcceptUserInsideRoomMessageComposer());
        }
    }
}
