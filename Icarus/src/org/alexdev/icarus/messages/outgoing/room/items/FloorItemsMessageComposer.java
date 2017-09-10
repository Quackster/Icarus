package org.alexdev.icarus.messages.outgoing.room.items;

import java.util.List;

import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.ItemExtraDataUtil;
import org.alexdev.icarus.messages.MessageComposer;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.util.Util;

public class FloorItemsMessageComposer extends MessageComposer {

    private List<Item> items;

    public FloorItemsMessageComposer(List<Item> list) {
        this.items = list;
    }

    @Override
    public void write() {
        
        this.response.init(Outgoing.FloorItemsMessageComposer);
        this.response.writeInt(items.size());
        
        for (Item floorItem : items) { 
            this.response.writeInt(floorItem.getOwnerId());
            this.response.writeString(floorItem.getOwnerName());
        }

        this.response.writeInt(items.size());

        for (Item floorItem : items) {
            
            this.response.writeInt(floorItem.getId());
            this.response.writeInt(floorItem.getDefinition().getSpriteId());
            this.response.writeInt(floorItem.getPosition().getX());
            this.response.writeInt(floorItem.getPosition().getY());
            this.response.writeInt(floorItem.getPosition().getRotation());
            this.response.writeString("" + Util.getDecimalFormatter().format(floorItem.getPosition().getZ()));
            this.response.writeString("");
  
            ItemExtraDataUtil.generateExtraData(floorItem, this.response);

            this.response.writeInt(-1);
            this.response.writeInt(floorItem.getDefinition().getInterationModes() > 0 ? 1 : 0);
            this.response.writeInt(floorItem.getOwnerId());
        }
    }
}
