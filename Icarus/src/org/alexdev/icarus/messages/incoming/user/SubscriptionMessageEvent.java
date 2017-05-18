package org.alexdev.icarus.messages.incoming.user;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.log.Log;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.outgoing.user.SubscriptionMessageComposer;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class SubscriptionMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {
       
        if (player.getSubscription().isExpired()) {
            return;
        }
        
        // testing log
        Log.println("Subscription not expired");

        player.send(new SubscriptionMessageComposer(player.getSubscription().getDaysLeft(), player.getSubscription().getMonthsLeft()));
    }

}
