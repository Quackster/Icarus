package org.alexdev.icarus.messages.incoming.navigator;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class CloseNavigatorTabMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {
        System.out.println("Close: " + reader.readString());
    }
}
