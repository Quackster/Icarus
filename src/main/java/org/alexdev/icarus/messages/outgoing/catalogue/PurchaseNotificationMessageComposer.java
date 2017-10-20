package org.alexdev.icarus.messages.outgoing.catalogue;

import org.alexdev.icarus.game.catalogue.CatalogueItem;
import org.alexdev.icarus.game.item.ItemDefinition;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;
import org.alexdev.icarus.server.api.messages.Response;

public class PurchaseNotificationMessageComposer extends MessageComposer {

    private CatalogueItem item;

    public PurchaseNotificationMessageComposer(CatalogueItem item) {
        this.item = item;
    }

    public PurchaseNotificationMessageComposer() {
    }

    @Override
    public void compose(Response response) {

        if (item == null) {
            response.writeInt(0);
            response.writeString("");
            response.writeBool(false);
            response.writeInt(0);
            response.writeInt(0);
            response.writeInt(0);
            response.writeBool(true);
            response.writeInt(1);
            response.writeString("s");
            response.writeInt(0);
            response.writeString("");
            response.writeInt(1);
            response.writeInt(0);
            response.writeString("");
            response.writeInt(1);

        } else {

            ItemDefinition definition = item.getItemDefinition();

            response.writeInt(definition.getId());
            response.writeString(definition.getItemName());
            response.writeBool(false);
            response.writeInt(item.getCostCredits());
            response.writeInt(item.getPixelCost());
            response.writeInt(0);
            response.writeBool(true);
            response.writeInt(1);
            response.writeString(definition.getType());
            response.writeInt(definition.getSpriteId());
            response.writeString(item.getExtraData());
            response.writeInt(item.getAmount());

            response.writeBool(item.getLimitedTotal() > 0);

            if (item.getLimitedTotal() > 0) {
                response.writeInt(item.getLimitedTotal());
                response.writeInt(item.getLimitedSells());
            }

            response.writeString(item.getSubscriptionStatus());
            response.writeInt(1);
        }
    }

    @Override
    public short getHeader() {
        return Outgoing.PurchaseNotificationMessageComposer;
    }
}