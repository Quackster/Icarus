package org.alexdev.icarus.messages.incoming.room.user;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.RoomUser;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.headers.Incoming;
import org.alexdev.icarus.messages.outgoing.room.user.TypingStatusMessageComposer;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class TypingStatusMessageEvent implements MessageEvent {

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
        
        room.send(new TypingStatusMessageComposer(roomUser.getVirtualID(), request.getMessageID() == Incoming.StartTypingMessageEvent));
    }
}
