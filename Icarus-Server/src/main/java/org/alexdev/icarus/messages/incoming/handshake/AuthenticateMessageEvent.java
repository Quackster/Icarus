package org.alexdev.icarus.messages.incoming.handshake;

import org.alexdev.icarus.game.GameSettings;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class AuthenticateMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage request) {
        String ssoTicket = request.readString();
        player.authenticate(ssoTicket);

        player.getMessageHandler().unregisterHandshakePackets();
    }
}
