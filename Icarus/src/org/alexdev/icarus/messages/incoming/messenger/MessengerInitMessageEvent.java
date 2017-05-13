package org.alexdev.icarus.messages.incoming.messenger;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.outgoing.messenger.FriendsListMessageComposer;
import org.alexdev.icarus.messages.outgoing.messenger.MessengerCategoriesMessageComposer;
import org.alexdev.icarus.messages.outgoing.messenger.MessengerRequestsMessageComposer;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class MessengerInitMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage request) {
        
        if (player.getMessenger() == null) {
            return;
        }
        
        player.getMessenger().load();
        
        player.send(new MessengerCategoriesMessageComposer());
        player.send(new MessengerRequestsMessageComposer(player, player.getMessenger().getRequests()));
        player.send(new FriendsListMessageComposer(player.getMessenger().getFriends()));
        
        player.getMessenger().setInitalised(true);
        player.getMessenger().sendStatus(false);
    }

}