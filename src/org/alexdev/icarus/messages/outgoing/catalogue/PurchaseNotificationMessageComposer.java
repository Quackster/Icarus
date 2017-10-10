package org.alexdev.icarus.messages.outgoing.catalogue;

import org.alexdev.icarus.game.catalogue.CatalogueItem;
import org.alexdev.icarus.game.item.ItemDefinition;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;

public class PurchaseNotificationMessageComposer extends MessageComposer {

    private CatalogueItem item;

    public PurchaseNotificationMessageComposer(CatalogueItem item) {
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
            
            this.response.writeInt(definition.getId());
            this.response.writeString(definition.getItemName());
            this.response.writeBool(false);
            this.response.writeInt(this.item.getCostCredits());
            this.response.writeInt(this.item.getCostOtherType());
            this.response.writeInt(0);
            this.response.writeBool(true);
            this.response.writeInt(1);
            this.response.writeString(definition.getType());
            this.response.writeInt(definition.getSpriteId());
            this.response.writeString(item.getExtraData());
            this.response.writeInt(item.getAmount());

            this.response.writeBool(item.getLimitedStack() > 0);

            if (this.item.getLimitedStack() > 0) {
                this.response.writeInt(item.getLimitedStack());
                this.response.writeInt(item.getLimitedSells());
            }

            this.response.writeString(item.getSubscriptionStatus());
            this.response.writeInt(1);
        }

    }
}
