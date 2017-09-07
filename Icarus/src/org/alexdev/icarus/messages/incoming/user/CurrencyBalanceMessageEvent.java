package org.alexdev.icarus.messages.incoming.user;

import java.util.HashMap;
import java.util.Map;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.outgoing.user.ActivityPointsComposer;
import org.alexdev.icarus.messages.outgoing.user.CreditsMessageComposer;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class CurrencyBalanceMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {

        /*Response response = new Response();
        response.init(Outgoing.ActivityPointsMessageComposer);
        response.appendInt32(2);
        response.appendInt32(0);
        response.appendInt32(1337);
        response.appendInt32(5);
        response.appendInt32(44444);
        player.send(response);*/
        
        player.send(new CreditsMessageComposer(player.getDetails().getCredits()));
        
        Map<Integer, Integer> currencies = new HashMap<Integer, Integer>();
        currencies.put(0, 5000);
        currencies.put(1, 16); // snowflakes
        currencies.put(2, 15); // hearts
        currencies.put(3, 14); // gift points 
        currencies.put(4, 13); // shells
        currencies.put(5, 10); // diamonds -- seasonal currency
        currencies.put(101, 10); // snowflakes
        currencies.put(103, 0); // stars -- some other currency
        currencies.put(104, 0); // clouds
        currencies.put(105, 0); // diamonds again??
        
        player.send(new ActivityPointsComposer(currencies));
    }
}
