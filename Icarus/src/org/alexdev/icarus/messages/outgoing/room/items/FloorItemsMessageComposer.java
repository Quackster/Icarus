package org.alexdev.icarus.messages.outgoing.room.items;

import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;

public class FloorItemsMessageComposer extends OutgoingMessageComposer {

    private Item[] items;

    public FloorItemsMessageComposer(Item[] items) {
        this.items = items;
    }

    @Override
    public void write() {
        
        response.init(Outgoing.FloorItemsMessageComposer);
        response.writeInt(items.length);
        
        for (Item floorItem : items) { 
            response.writeInt(floorItem.getUserId());
            response.writeString(floorItem.getOwnerData().getUsername());
        }

        response.writeInt(items.length);

        for (Item floorItem : items) {
            floorItem.getSerializer().serialiseItem(response);
        }
    }
}
