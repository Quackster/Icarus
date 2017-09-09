package org.alexdev.icarus.messages.incoming.messenger;

import org.alexdev.icarus.dao.mysql.player.MessengerDao;
import org.alexdev.icarus.game.messenger.MessengerUser;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class MessengerDeclineMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage request) {

        boolean deleteAll = request.readBoolean();

        if (!deleteAll) {

            request.readInt();
            int fromID = request.readInt();
            
            MessengerDao.removeRequest(fromID, player.getDetails().getID());
            
        } else {
            
            for (int i = 0; i < player.getMessenger().getRequests().size(); i++) {
                
                MessengerUser user = player.getMessenger().getRequests().get(i);
                MessengerDao.removeRequest(user.getUserID(), player.getDetails().getID());
                player.getMessenger().getRequests().remove(user);
            }
        }
    }
}
