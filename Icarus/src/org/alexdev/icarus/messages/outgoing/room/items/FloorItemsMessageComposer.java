package org.alexdev.icarus.messages.outgoing.room.items;

import java.util.List;

import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.ItemExtraDataUtil;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.MessageComposer;
import org.alexdev.icarus.util.Util;

public class FloorItemsMessageComposer extends MessageComposer {

    private List<Item> items;

    public FloorItemsMessageComposer(List<Item> list) {
        this.items = list;
    }

    @Override
    public void write() {
        
        response.init(Outgoing.FloorItemsMessageComposer);
        response.writeInt(items.size());
        
        for (Item floorItem : items) { 
            response.writeInt(floorItem.getUserId());
            response.writeString(floorItem.getOwnerData().getName());
        }

        response.writeInt(items.size());

        for (Item floorItem : items) {
            
            response.writeInt(floorItem.getId());
            response.writeInt(floorItem.getDefinition().getSpriteId());
            response.writeInt(floorItem.getPosition().getX());
            response.writeInt(floorItem.getPosition().getY());
            response.writeInt(floorItem.getPosition().getRotation());
            response.writeString("" + Util.getDecimalFormatter().format(floorItem.getPosition().getZ()));
            response.writeString("");
  
            ItemExtraDataUtil.generateExtraData(floorItem, this.response);

            response.writeInt(-1);
            response.writeInt(floorItem.getDefinition().getInterationModes() > 0 ? 1 : 0);
            response.writeInt(floorItem.getOwnerData().getId());
        }
    }
}
