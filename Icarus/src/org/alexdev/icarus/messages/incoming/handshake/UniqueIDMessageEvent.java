package org.alexdev.icarus.messages.incoming.handshake;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class UniqueIDMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage request) {

        request.readString();
        player.setMachineID(request.readString());
    }
}
