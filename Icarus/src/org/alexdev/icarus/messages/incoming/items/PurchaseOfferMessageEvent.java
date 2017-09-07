package org.alexdev.icarus.messages.incoming.items;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.log.Log;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class PurchaseOfferMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {
       
        int targetedOfferId = reader.readInt();
        
        Log.println("Purchase targeted offer with ID " + targetedOfferId);
    }
}
