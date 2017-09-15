package org.alexdev.icarus.messages.incoming.handshake;

import org.alexdev.icarus.dao.mysql.player.PlayerDao;
import org.alexdev.icarus.dao.mysql.room.RoomDao;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.player.PlayerManager;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.outgoing.handshake.AuthenticationOKMessageComposer;
import org.alexdev.icarus.messages.outgoing.handshake.AvailabilityMessageComposer;
import org.alexdev.icarus.messages.outgoing.handshake.UniqueMachineIDMessageComposer;
import org.alexdev.icarus.messages.outgoing.user.HomeRoomMessageComposer;
import org.alexdev.icarus.messages.outgoing.user.LandingWidgetMessageComposer;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class AuthenticateMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage request) {
        
        if (player.getDetails().isAuthenticated()) {
            return;
        }
        
        String sso = request.readString();
        
        boolean loginSuccess = PlayerDao.login(player, sso);
        
        if (!loginSuccess) {
            player.getNetwork().close();
            return;
        }
        
        if (player.getMachineId() == null) {
            player.getNetwork().close();
            return;
        }
        
        if (PlayerManager.kickDuplicates(player)) {
            player.getNetwork().close();
            return;
        }
   
        player.send(new UniqueMachineIDMessageComposer(player.getMachineId()));
        player.send(new AuthenticationOKMessageComposer());
        player.send(new HomeRoomMessageComposer(2, false));
        player.send(new LandingWidgetMessageComposer());
        player.send(new AvailabilityMessageComposer());
        
        PlayerManager.addPlayer(player);
        RoomDao.getPlayerRooms(player.getDetails().getId(), true);
        
        player.getInventory().init();
        player.getMessenger().init();
        
        player.getDetails().setAuthenticated(true);
    }
}
