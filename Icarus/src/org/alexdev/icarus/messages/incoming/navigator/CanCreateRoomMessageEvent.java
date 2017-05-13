package org.alexdev.icarus.messages.incoming.navigator;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.outgoing.navigator.CanCreateRoomMessageComposer;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class CanCreateRoomMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage request) {
        player.send(new CanCreateRoomMessageComposer(player));
    }
}
