package org.alexdev.icarus.messages.incoming.items;

import org.alexdev.icarus.game.catalogue.CatalogueManager;
import org.alexdev.icarus.game.catalogue.targetedoffer.TargetedOffer;
import org.alexdev.icarus.game.furniture.ItemDefinition;
import org.alexdev.icarus.game.furniture.ItemManager;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.outgoing.catalogue.PurchaseNotificationMessageComposer;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class PurchaseOfferMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {
       
        int targetedOfferID = reader.readInt();
        
        TargetedOffer offer = CatalogueManager.getOfferByID(targetedOfferID);
        
        if (offer == null) {
            return;
        }
        
        if (offer.isExpired()) {
            return;
        }
        
        for (int definitionID : offer.getItems()) {
            
            ItemDefinition definition = ItemManager.getFurnitureByID(definitionID);
            definition.handleDefinitionPurchase(player, "");
        }
       
        player.getDetails().sendCredits();
        player.send(new PurchaseNotificationMessageComposer());
        
        offer.addUserToBlacklist(player.getDetails().getID());
    }
}
