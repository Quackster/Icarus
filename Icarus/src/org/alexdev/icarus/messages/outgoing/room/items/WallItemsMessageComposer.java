package org.alexdev.icarus.messages.outgoing.room.items;

import java.util.List;

import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.messages.MessageComposer;
import org.alexdev.icarus.messages.headers.Outgoing;

public class WallItemsMessageComposer extends MessageComposer {

    private List<Item> items;

    public WallItemsMessageComposer(List<Item> list) {
        this.items = list;
    }

    @Override
    public void write() {
        
        this.response.init(Outgoing.WallItemsMessageComposer);
        
        this.response.writeInt(this.items.size());
        for (Item wallItem : this.items) { 
            this.response.writeInt(wallItem.getOwnerId());
            this.response.writeString(wallItem.getOwnerName());
        }

        this.response.writeInt(this.items.size());
        
        for (Item wallItem : this.items) {
            this.response.writeString(wallItem.getId() + "");
            this.response.writeInt(wallItem.getDefinition().getSpriteId());
            this.response.writeString(wallItem.getWallPosition());
            this.response.writeString(wallItem.getExtraData());
            this.response.writeInt(-1);
            this.response.writeInt(wallItem.getDefinition().getInteractionModes() > 0 ? 1 : 0);
            this.response.writeInt(wallItem.getOwnerId());
        }
    }
}
