package org.alexdev.icarus.messages.outgoing.room.items;

import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.ItemType;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;

public class MoveItemMessageComposer extends OutgoingMessageComposer {

    private Item item;

    public MoveItemMessageComposer(Item item) {
        this.item = item;
    }

    @Override
    public void write() {
       
        if (this.item.getType() == ItemType.WALL) {
            this.response.init(Outgoing.MoveWallItemMessageComposer);
        }

        if (this.item.getType() == ItemType.FLOOR) {
            this.response.init(Outgoing.MoveFloorItemMessageComposer);
        }
        
        this.item.getSerializer().serialiseItem(this.response);
    }

}
