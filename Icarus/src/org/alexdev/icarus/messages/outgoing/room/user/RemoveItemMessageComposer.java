package org.alexdev.icarus.messages.outgoing.room.user;

import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.ItemType;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.MessageComposer;

public class RemoveItemMessageComposer extends MessageComposer {

    private Item item;
    
    public RemoveItemMessageComposer(Item item) {
        this.item = item;
    }

    @Override
    public void write() {
        
        if (this.item.getType() == ItemType.FLOOR) {
            this.response.init(Outgoing.RemoveItemMessageComposer);
        }
        
        if (this.item.getType() == ItemType.WALL) {
            this.response.init(Outgoing.RemoveWallItemMessageComposer);
        }
        
        this.response.writeString(item.getId());
        this.response.writeBool(false);
        this.response.writeInt(item.getUserId());

        if (this.item.getType() == ItemType.FLOOR) {
            this.response.writeInt(0);
        }
    }
}
