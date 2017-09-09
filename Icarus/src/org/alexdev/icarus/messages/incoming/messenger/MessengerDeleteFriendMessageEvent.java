package org.alexdev.icarus.messages.incoming.messenger;

import org.alexdev.icarus.dao.mysql.player.MessengerDao;
import org.alexdev.icarus.game.messenger.MessengerUser;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.outgoing.messenger.RemoveFriendMessageComposer;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class MessengerDeleteFriendMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage request) {

        int amount = request.readInt();

        for (int i = 0; i < amount; i++) {

            int friendID = request.readInt();

            if (player.getMessenger().isFriend(friendID)) {

                MessengerUser friend = player.getMessenger().getFriend(friendID);

                if (friend.isOnline()) {
                    friend.getPlayer().getMessenger().removeFriend(player.getDetails().getID());
                    friend.getPlayer().send(new RemoveFriendMessageComposer(player.getDetails().getID()));
                }    
                
                player.getMessenger().removeFriend(friendID);
                player.send(new RemoveFriendMessageComposer(friendID));
                
                MessengerDao.removeFriend(friendID, player.getDetails().getID());
            }
        }
    }
}
