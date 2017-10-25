package org.alexdev.icarus.messages.incoming.items;

import org.alexdev.icarus.game.catalogue.CatalogueManager;
import org.alexdev.icarus.game.catalogue.targetedoffer.TargetedOffer;
import org.alexdev.icarus.game.item.ItemDefinition;
import org.alexdev.icarus.game.item.ItemManager;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.outgoing.catalogue.PurchaseNotificationMessageComposer;
import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class PurchaseOfferMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {
       
        int targetedOfferId = reader.readInt();
        
        TargetedOffer offer = CatalogueManager.getInstance().getOfferById(targetedOfferId);
        
        if (offer == null) {
            return;
        }
        
        if (offer.isExpired()) {
            return;
        }
        
        for (int definitionId : offer.getItems()) {
            ItemDefinition definition = ItemManager.getInstance().getFurnitureById(definitionId);
            definition.handlePurchase(player, "");
        }
       
        player.getDetails().sendCredits();
        player.send(new PurchaseNotificationMessageComposer());
        
        offer.addUserToBlacklist(player.getEntityId());
    }
}
