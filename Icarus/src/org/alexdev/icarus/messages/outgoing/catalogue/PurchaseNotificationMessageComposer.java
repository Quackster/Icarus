package org.alexdev.icarus.messages.outgoing.catalogue;

import org.alexdev.icarus.game.catalogue.CatalogueBundledItem;
import org.alexdev.icarus.game.furniture.ItemManager;
import org.alexdev.icarus.game.furniture.ItemDefinition;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;

public class PurchaseNotificationMessageComposer extends OutgoingMessageComposer {
    
    private CatalogueBundledItem item;
    
    public PurchaseNotificationMessageComposer(CatalogueBundledItem item) {
        this.item = item;
    }

    @Override
    public void write() {
        
        ItemDefinition definition = ItemManager.getFurnitureById(item.getItemId());
        
        response.init(Outgoing.PurchaseNotificationMessageComposer);
        response.writeInt(definition.getId());
        response.writeString(definition.getItemName());
        response.writeBool(false);
        response.writeInt(item.getCatalogueItem().getCostCredits());
        response.writeInt(item.getCatalogueItem().getCostPixels());
        response.writeInt(0);
        response.writeBool(true);
        
        response.writeInt(1); // amount
        
        response.writeString(definition.getType());
        response.writeInt(definition.getSpriteId());
        response.writeString(item.getExtraData());
        response.writeInt(item.getAmount());
        
        response.writeBool(item.getCatalogueItem().getLimitedTotal() > 0);
        
        if (item.getCatalogueItem().getLimitedTotal() > 0) {
            response.writeInt(item.getCatalogueItem().getLimitedTotal());
            response.writeInt(item.getCatalogueItem().getLimitedSells());
        }
        
        response.writeString(item.getCatalogueItem().getSubscriptionStatus());
        response.writeInt(1);
        
    }

}
