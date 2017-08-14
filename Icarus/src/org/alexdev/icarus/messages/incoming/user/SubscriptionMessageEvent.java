package org.alexdev.icarus.messages.incoming.user;

import org.alexdev.icarus.dao.mysql.ClubDao;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.log.Log;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.outgoing.user.SubscriptionMessageComposer;
import org.alexdev.icarus.messages.outgoing.user.UserRightsComposer;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class SubscriptionMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {

        long[] subscriptionData = ClubDao.getSubscription(player.getDetails().getId());
        
        if (subscriptionData != null) {
            player.getSubscription().update(player.getDetails().getId(), subscriptionData[0], subscriptionData[1]);
            
        }
        
        player.send(new SubscriptionMessageComposer(player));
        player.send(new UserRightsComposer(player.getSubscription().hasSubscription(), player.getDetails().getRank()));
        
    }

}
