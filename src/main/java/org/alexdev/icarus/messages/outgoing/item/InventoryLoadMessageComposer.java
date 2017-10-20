package org.alexdev.icarus.messages.outgoing.item;

import java.util.Collection;

import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.ItemType;
import org.alexdev.icarus.game.util.ItemUtil;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class InventoryLoadMessageComposer extends MessageComposer {

    private Collection<Item> items;

    public InventoryLoadMessageComposer(Collection<Item> collection) {
        this.items = collection;
    }

    @Override
    public void compose(Response response) {

        //response.init(Outgoing.InventoryMessageComposer);
        response.writeInt(1);
        response.writeInt(0);
        response.writeInt(this.items.size());

        for (Item item : this.items) {
            response.writeInt(item.getId());
            response.writeString(item.getDefinition().getType().toString().toUpperCase());
            response.writeInt(item.getId());
            response.writeInt(item.getDefinition().getSpriteId());
            ItemUtil.generateExtraData(item, response);
            response.writeBool(item.getDefinition().allowRecycle());
            response.writeBool(item.getDefinition().allowTrade());
            response.writeBool(item.getDefinition().allowInventoryStack());
            response.writeBool(item.getDefinition().allowMarketplaceSell());
            response.writeInt(-1);
            response.writeBool(false);
            response.writeInt(-1);

            if (item.getDefinition().getType() != ItemType.WALL) {
                response.writeString("");
                response.writeInt(0);
            }
        }
    }

    @Override
    public short getHeader() {
        return Outgoing.InventoryMessageComposer;
    }
}
