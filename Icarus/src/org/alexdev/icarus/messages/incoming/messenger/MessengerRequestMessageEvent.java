package org.alexdev.icarus.messages.incoming.messenger;

import org.alexdev.icarus.dao.mysql.player.MessengerDao;
import org.alexdev.icarus.dao.mysql.player.PlayerDao;
import org.alexdev.icarus.game.messenger.MessengerUser;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.log.Log;
import org.alexdev.icarus.messages.outgoing.messenger.MessengerSendRequest;
import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class MessengerRequestMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage request) {

        String username = request.readString();

        if (username == null) {
            return;
        }
        
        Log.println("test 1");

        int userId = PlayerDao.getId(username);

        if (userId < 1) {
            return;
        }
        
        Log.println("test 2");

        if (player.getMessenger().hasRequest(userId)) {
            return;
        }
        
        Log.println("test 3");

        //TODO: Check if they have blocked friend requests

        if (MessengerDao.newRequest(player.getEntityId(), userId)) {

            Log.println("test 4");
            
            MessengerUser user = new MessengerUser(userId);
            player.getMessenger().getRequests().add(user);

            if (user.isUserOnline()) {
                user.getPlayer().send(new MessengerSendRequest(player.getEntityId(), player.getDetails().getName(), player.getDetails().getFigure()));
            }
        }
    }
}
