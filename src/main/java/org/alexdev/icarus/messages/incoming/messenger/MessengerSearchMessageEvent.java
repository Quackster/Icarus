package org.alexdev.icarus.messages.incoming.messenger;

import java.util.ArrayList;
import java.util.List;

import org.alexdev.icarus.dao.mysql.player.MessengerDao;
import org.alexdev.icarus.game.messenger.Messenger;
import org.alexdev.icarus.game.messenger.MessengerUser;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.outgoing.messenger.MessengerSearchMessageComposer;
import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class MessengerSearchMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage request) {
        
        String searchQuery = request.readString();
        
        if (searchQuery == null) {
            return;
        }

        Messenger messenger = player.getMessenger();
        
        if (messenger == null) {
            return;
        }
        
        List<Integer> search = MessengerDao.search(searchQuery);
        List<MessengerUser> friends = new ArrayList<>();
        List<MessengerUser> strangers = new ArrayList<>();

        for (Integer id : search) {

            if (id != player.getEntityId()) {

                if (player.getMessenger().isFriend(id)) {
                    friends.add(player.getMessenger().getFriend(id));
                } else {
                    strangers.add(new MessengerUser(id));
                }
            }
        }
        
        player.send(new MessengerSearchMessageComposer(friends, strangers));
    }
}
