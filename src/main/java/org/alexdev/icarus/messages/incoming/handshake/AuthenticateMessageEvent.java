package org.alexdev.icarus.messages.incoming.handshake;

import org.alexdev.icarus.dao.mysql.player.PlayerDao;
import org.alexdev.icarus.dao.mysql.room.RoomDao;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.player.PlayerManager;
import org.alexdev.icarus.messages.outgoing.handshake.AuthenticationOKMessageComposer;
import org.alexdev.icarus.messages.outgoing.handshake.AvailabilityMessageComposer;
import org.alexdev.icarus.messages.outgoing.handshake.UniqueMachineIDMessageComposer;
import org.alexdev.icarus.messages.outgoing.user.HomeRoomMessageComposer;
import org.alexdev.icarus.messages.outgoing.user.LandingWidgetMessageComposer;
import org.alexdev.icarus.messages.outgoing.user.effects.EffectsMessageComposer;
import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class AuthenticateMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage request) {
        
        if (player.getRc4() == null) {
            player.getNetwork().close();
            return;
        }

        String ssoTicket = request.readString();
        player.authenticate(ssoTicket);

        player.getMessageHandler().unregisterHandshakePackets();
    }
}
