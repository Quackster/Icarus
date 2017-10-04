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
        
        if (player.getDetails().isAuthenticated() || player.getRc4() == null) {
            return;
        }
        
        boolean loginSuccess = PlayerDao.login(player, request.readString());
        PlayerDao.clearTicket(player.getDetails().getId());
        
        if (!loginSuccess || player.getMachineId() == null || PlayerManager.kickDuplicates(player)) {
            player.getNetwork().close();
            return;
        }
           
        PlayerManager.addPlayer(player);
        RoomDao.getPlayerRooms(player.getEntityId(), true);
        
        player.getInventory().init();
        player.getMessenger().init();
        
        player.getDetails().setAuthenticated(true);
        
        player.send(new UniqueMachineIDMessageComposer(player.getMachineId()));
        player.send(new AuthenticationOKMessageComposer());
        player.send(new EffectsMessageComposer(player.getInventory().getEffects()));
        player.send(new HomeRoomMessageComposer(2, false));
        player.send(new LandingWidgetMessageComposer());
        player.send(new AvailabilityMessageComposer());

    }
}
