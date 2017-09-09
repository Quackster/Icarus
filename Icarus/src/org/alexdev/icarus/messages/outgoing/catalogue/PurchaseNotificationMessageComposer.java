package org.alexdev.icarus.messages.outgoing.catalogue;

import org.alexdev.icarus.game.catalogue.CatalogueBundledItem;
import org.alexdev.icarus.game.furniture.ItemDefinition;
import org.alexdev.icarus.messages.MessageComposer;
import org.alexdev.icarus.messages.headers.Outgoing;

public class PurchaseNotificationMessageComposer extends MessageComposer {

    private CatalogueBundledItem item;

    public PurchaseNotificationMessageComposer(CatalogueBundledItem item) {
        this.item = item;
    }

    public PurchaseNotificationMessageComposer() { }

    @Override
    public void write() {

        this.response.init(Outgoing.PurchaseNotificationMessageComposer);
            
        if (this.item == null) {
            this.response.writeInt(0);
            this.response.writeString("");
            this.response.writeBool(false);
            this.response.writeInt(0);
            this.response.writeInt(0);
            this.response.writeInt(0);
            this.response.writeBool(true);
            this.response.writeInt(1);
            this.response.writeString("s");
            this.response.writeInt(0);
            this.response.writeString("");
            this.response.writeInt(1);
            this.response.writeInt(0);
            this.response.writeString("");
            this.response.writeInt(1);

        } else {
            
            ItemDefinition definition = item.getItemDefinition();
            
            this.response.writeInt(definition.getID());
            this.response.writeString(definition.getItemName());
            this.response.writeBool(false);
            this.response.writeInt(this.item.getCatalogueItem().getCostCredits());
            this.response.writeInt(this.item.getCatalogueItem().getCostPixels());
            this.response.writeInt(0);
            this.response.writeBool(true);
            this.response.writeInt(1);
            this.response.writeString(definition.getType());
            this.response.writeInt(definition.getSpriteID());
            this.response.writeString(item.getExtraData());
            this.response.writeInt(item.getAmount());

            this.response.writeBool(item.getCatalogueItem().getLimitedTotal() > 0);

            if (item.getCatalogueItem().getLimitedTotal() > 0) {
                this.response.writeInt(item.getCatalogueItem().getLimitedTotal());
                this.response.writeInt(item.getCatalogueItem().getLimitedSells());
            }

            this.response.writeString(item.getCatalogueItem().getSubscriptionStatus());
            this.response.writeInt(1);
        }

    }
}
