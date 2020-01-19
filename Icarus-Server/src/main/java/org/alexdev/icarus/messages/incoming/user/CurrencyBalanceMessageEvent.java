package org.alexdev.icarus.messages.incoming.user;

import java.util.HashMap;
import java.util.Map;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.outgoing.user.ActivityPointsMessageComposer;
import org.alexdev.icarus.messages.outgoing.user.CreditsMessageComposer;
import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class CurrencyBalanceMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {
        /*Response response = new Response();
        this.response.init(Outgoing.ActivityPointsMessageComposer);
        this.response.appendInt32(2);
        this.response.appendInt32(0);
        this.response.appendInt32(1337);
        this.response.appendInt32(5);
        this.response.appendInt32(44444);
        player.send(response);*/
        
        player.sendQueued(new CreditsMessageComposer(player.getDetails().getCredits()));
        
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
        
        player.sendQueued(new ActivityPointsMessageComposer(currencies));
        player.flushQueue();
    }
}
