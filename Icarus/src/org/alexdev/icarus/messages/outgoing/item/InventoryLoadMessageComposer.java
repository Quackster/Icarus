package org.alexdev.icarus.messages.outgoing.item;

import java.util.List;

import org.alexdev.icarus.game.furniture.interactions.InteractionType;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;

public class InventoryLoadMessageComposer extends MessageComposer {

    private List<Item> wallItems;
    private List<Item> floorItems;

    public InventoryLoadMessageComposer(List<Item> wallItems, List<Item> floorItems) {
        this.wallItems = wallItems;
        this.floorItems = floorItems;
    }

    @Override
    public void write() {

        this.response.init(Outgoing.InventoryMessageComposer);
        this.response.writeInt(1);
        this.response.writeInt(0);
        this.response.writeInt(this.wallItems.size() + this.floorItems.size());

        for (Item item : this.wallItems) {
            this.response.writeInt(item.getId());
            this.response.writeString(item.getDefinition().getType().toUpperCase());
            this.response.writeInt(item.getId());
            this.response.writeInt(item.getDefinition().getSpriteId());

            if (item.getDefinition().getItemName().contains("landscape"))
                this.response.writeInt(4);
            else if (item.getDefinition().getItemName().contains("wallpaper"))
                this.response.writeInt(2);
            else if (item.getDefinition().getItemName().contains("a2")) 
                this.response.writeInt(3);
            else
                this.response.writeInt(1);

            this.response.writeInt(0);
            this.response.writeString(item.getExtraData());
            this.response.writeBool(item.getDefinition().allowRecycle());
            this.response.writeBool(item.getDefinition().allowTrade());
            this.response.writeBool(item.getDefinition().allowInventoryStack());
            this.response.writeBool(item.getDefinition().allowMarketplaceSell());
            this.response.writeInt(-1);
            this.response.writeBool(false);
            this.response.writeInt(-1);
        }

        for (Item item : floorItems) {
            this.response.writeInt(item.getId());
            this.response.writeString(item.getDefinition().getType().toUpperCase());
            this.response.writeInt(item.getId());
            this.response.writeInt(item.getDefinition().getSpriteId());

            if (item.getDefinition().getInteractionType() == InteractionType.GROUPITEM || item.getDefinition().getInteractionType() == InteractionType.GLD_GATE) {
                this.response.writeInt(17); 
            } else if (item.getDefinition().getInteractionType() == InteractionType.MUSICDISK) {
                this.response.writeInt(8);
            } else {
                this.response.writeInt(1);
            }

            this.response.writeInt(0);
            this.response.writeString(item.getExtraData());
            this.response.writeBool(item.getDefinition().allowRecycle());
            this.response.writeBool(item.getDefinition().allowTrade());
            this.response.writeBool(item.getDefinition().allowInventoryStack());
            this.response.writeBool(item.getDefinition().allowMarketplaceSell());
            this.response.writeInt(-1);
            this.response.writeBool(false); 
            this.response.writeInt(-1);
            this.response.writeString("");
            this.response.writeInt(0);
        }
    }
}
