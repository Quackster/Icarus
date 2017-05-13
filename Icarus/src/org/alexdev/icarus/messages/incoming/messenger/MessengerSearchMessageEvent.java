package org.alexdev.icarus.messages.incoming.messenger;

import java.util.List;

import org.alexdev.icarus.dao.mysql.MessengerDao;
import org.alexdev.icarus.game.messenger.Messenger;
import org.alexdev.icarus.game.messenger.MessengerUser;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.outgoing.messenger.MessengerSearchMessageComposer;
import org.alexdev.icarus.server.api.messages.ClientMessage;

import com.google.common.collect.Lists;

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
        
        if (!messenger.hasInitalised()) {
            return;
        }
        
        List<Integer> search = MessengerDao.search(searchQuery);
        List<MessengerUser> friends = Lists.newArrayList();
        List<MessengerUser> strangers = Lists.newArrayList();

        for (Integer id : search) {

            if (id != player.getDetails().getId()) {

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
