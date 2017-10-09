package org.alexdev.icarus.messages.incoming.user.club;

import org.alexdev.icarus.dao.mysql.catalogue.ClubDao;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class SubscriptionMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {

        if (!(player.getSubscription().getUserId() > 0)) {
            
            long[] subscriptionData = ClubDao.getSubscription(player.getEntityId());

            if (subscriptionData != null) {
                player.getSubscription().update(player.getEntityId(), subscriptionData[0], subscriptionData[1]);

            }
        }

        player.getSubscription().sendSubscriptionStatus();

    }
}
