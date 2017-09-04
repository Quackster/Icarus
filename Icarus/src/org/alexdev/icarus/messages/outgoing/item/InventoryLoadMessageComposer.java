package org.alexdev.icarus.messages.outgoing.item;

import java.util.List;

import org.alexdev.icarus.game.furniture.interactions.InteractionType;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.MessageComposer;

public class InventoryLoadMessageComposer extends MessageComposer {

    private List<Item> wallItems;
    private List<Item> floorItems;

    public InventoryLoadMessageComposer(Player player) {
        this(player.getInventory().getWallItems(), player.getInventory().getFloorItems());
    }

    public InventoryLoadMessageComposer(List<Item> wallItems, List<Item> floorItems) {
        this.wallItems = wallItems;
        this.floorItems = floorItems;
    }

    @Override
    public void write() {

        response.init(Outgoing.InventoryMessageComposer);
        response.writeInt(1);
        response.writeInt(0);
        response.writeInt(this.wallItems.size() + this.floorItems.size());

        for (Item item : this.wallItems) {

            response.writeInt(item.getId());
            response.writeString(item.getDefinition().getType().toUpperCase());
            response.writeInt(item.getId());
            response.writeInt(item.getDefinition().getSpriteId());

            if (item.getDefinition().getItemName().contains("landscape"))
                response.writeInt(4);
            else if (item.getDefinition().getItemName().contains("wallpaper"))
                response.writeInt(2);
            else if (item.getDefinition().getItemName().contains("a2")) 
                response.writeInt(3);
            else
                response.writeInt(1);

            response.writeInt(0);
            response.writeString(item.getExtraData());
            response.writeBool(item.getDefinition().allowRecycle());
            response.writeBool(item.getDefinition().allowTrade());
            response.writeBool(item.getDefinition().allowInventoryStack());
            response.writeBool(item.getDefinition().allowMarketplaceSell());
            response.writeInt(-1);
            response.writeBool(false);
            response.writeInt(-1);
        }

        for (Item item : floorItems) {

            response.writeInt(item.getId());
            response.writeString(item.getDefinition().getType().toUpperCase());
            response.writeInt(item.getId());
            response.writeInt(item.getDefinition().getSpriteId());

            if (item.getDefinition().getInteractionType() == InteractionType.GROUPITEM || item.getDefinition().getInteractionType() == InteractionType.GLD_GATE) {
                response.writeInt(17); 
            } else if (item.getDefinition().getInteractionType() == InteractionType.MUSICDISK) {
                response.writeInt(8);
            } else {
                response.writeInt(1);
            }

            response.writeInt(0);
            response.writeString(item.getExtraData());
            response.writeBool(item.getDefinition().allowRecycle());
            response.writeBool(item.getDefinition().allowTrade());
            response.writeBool(item.getDefinition().allowInventoryStack());
            response.writeBool(item.getDefinition().allowMarketplaceSell());
            response.writeInt(-1);
            response.writeBool(false); 
            response.writeInt(-1);
            response.writeString("");
            response.writeInt(0);
        }

    }

}
