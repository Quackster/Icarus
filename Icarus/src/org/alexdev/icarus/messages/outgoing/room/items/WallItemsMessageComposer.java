package org.alexdev.icarus.messages.outgoing.room.items;

import java.util.List;

import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.MessageComposer;

public class WallItemsMessageComposer extends MessageComposer {

    private List<Item> items;

    public WallItemsMessageComposer(List<Item> list) {
        this.items = list;
    }

    @Override
    public void write() {
        
        response.init(Outgoing.WallItemsMessageComposer);
        
        response.writeInt(this.items.size());
        for (Item wallItem : this.items) { 
            response.writeInt(wallItem.getUserId());
            response.writeString(wallItem.getOwnerData().getUsername());
        }

        response.writeInt(this.items.size());
        
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
