package org.alexdev.icarus.messages.outgoing.room.items;

import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.ItemMetaDataUtil;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.util.Util;

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
        	
            response.writeInt(floorItem.getId());
            response.writeInt(floorItem.getDefinition().getSpriteId());
            response.writeInt(floorItem.getPosition().getX());
            response.writeInt(floorItem.getPosition().getY());
            response.writeInt(floorItem.getPosition().getRotation());
            response.writeString("" + Util.getDecimalFormatter().format(floorItem.getPosition().getZ()));
            response.writeString("");
  
            ItemMetaDataUtil.generateExtraData(floorItem, this.response);

            response.writeInt(-1);
            response.writeInt(floorItem.getDefinition().getInterationModes() > 0 ? 1 : 0);
            response.writeInt(floorItem.getOwnerData().getId());
        }
    }
}
