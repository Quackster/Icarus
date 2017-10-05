package org.alexdev.icarus.messages.outgoing.item;

import java.util.Collection;
import java.util.List;

import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.ItemType;
import org.alexdev.icarus.game.util.ItemUtil;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;

public class InventoryLoadMessageComposer extends MessageComposer {

    private Collection<Item> items;

    public InventoryLoadMessageComposer(Collection<Item> collection) {
        this.items = collection;
    }

    @Override
    public void write() {

        this.response.init(Outgoing.InventoryMessageComposer);
        this.response.writeInt(1);
        this.response.writeInt(0);
        this.response.writeInt(this.items.size());

        for (Item item : this.items) {
            this.response.writeInt(item.getId());
            this.response.writeString(item.getDefinition().getType().toString().toUpperCase());
            this.response.writeInt(item.getId());
            this.response.writeInt(item.getDefinition().getSpriteId()); 
            ItemUtil.generateExtraData(item, this.response);
            this.response.writeBool(item.getDefinition().allowRecycle());
            this.response.writeBool(item.getDefinition().allowTrade());
            this.response.writeBool(item.getDefinition().allowInventoryStack());
            this.response.writeBool(item.getDefinition().allowMarketplaceSell());
            this.response.writeInt(-1);
            this.response.writeBool(false);
            this.response.writeInt(-1);
            
            if (item.getDefinition().getType() != ItemType.WALL) {
                this.response.writeString("");
                this.response.writeInt(0);
            }
        }
    }
}
