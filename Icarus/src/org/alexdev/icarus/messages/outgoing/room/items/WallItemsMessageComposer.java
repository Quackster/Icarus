package org.alexdev.icarus.messages.outgoing.room.items;

import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;

public class WallItemsMessageComposer extends OutgoingMessageComposer {

    private Item[] items;

    public WallItemsMessageComposer(Item[] items) {
        this.items = items;
    }

    @Override
    public void write() {
        
        response.init(Outgoing.WallItemsMessageComposer);
        
        response.writeInt(this.items.length);
        for (Item wallItem : this.items) { 
            response.writeInt(wallItem.getUserId());
            response.writeString(wallItem.getOwnerData().getUsername());
        }

        response.writeInt(this.items.length);
        
        for (Item wallItem : this.items) {
            response.writeString(wallItem.getId() + "");
            response.writeInt(wallItem.getDefinition().getSpriteId());
            response.writeString(wallItem.getWallPosition());
            response.writeString(wallItem.getExtraData());
            response.writeInt(-1);
            response.writeInt(wallItem.getDefinition().getInterationModes() > 0 ? 1 : 0);
            response.writeInt(wallItem.getUserId());
        }
    }

}
