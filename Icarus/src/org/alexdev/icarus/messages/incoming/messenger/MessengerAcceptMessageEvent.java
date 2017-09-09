package org.alexdev.icarus.messages.incoming.messenger;

import org.alexdev.icarus.dao.mysql.player.MessengerDao;
import org.alexdev.icarus.game.messenger.MessengerUser;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.outgoing.messenger.MessengerUpdateMessageComposer;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class MessengerAcceptMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage request) {
        
        int amount = request.readInt();
    
        for (int i = 0; i < amount; i++) {
            
            int toID = player.getDetails().getID();
            int fromID = request.readInt();
            
            MessengerDao.removeRequest(fromID, toID);
            MessengerDao.newFriend(toID, fromID);
            
            MessengerUser user = new MessengerUser(fromID);
            player.getMessenger().getFriends().add(user);
            
            if (user.isOnline()) {
                
                MessengerUser to = new MessengerUser(toID);
                
                user.getPlayer().getMessenger().getFriends().add(to);
                user.getPlayer().send(new MessengerUpdateMessageComposer(to, false));
            }
            
            player.send(new MessengerUpdateMessageComposer(user, false));
        }
    }
}
