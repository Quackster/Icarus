package org.alexdev.icarus.messages.outgoing.room.items;

import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.ItemType;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;

public class PlaceItemMessageComposer extends OutgoingMessageComposer {

    private Item item;

    public PlaceItemMessageComposer(Item item) {
        this.item = item;
    }

    @Override
    public void write() {
        
        if (this.item.getType() == ItemType.FLOOR) {
            response.init(Outgoing.PlaceFloorItemMessageComposer); 
        }
        
        if (this.item.getType() == ItemType.WALL) {
            response.init(Outgoing.PlaceWallItemMessageComposer);
        }
        
        this.item.getSerializer().serialiseItem(response);
        response.writeString(this.item.getOwnerData().getUsername());
        
    }

}
