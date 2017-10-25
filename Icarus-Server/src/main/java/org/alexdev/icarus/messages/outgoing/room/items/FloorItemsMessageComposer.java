package org.alexdev.icarus.messages.outgoing.room.items;

import java.util.List;

import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.util.ItemUtil;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;
import org.alexdev.icarus.util.Util;

public class FloorItemsMessageComposer extends MessageComposer {

    private List<Item> items;

    public FloorItemsMessageComposer(List<Item> list) {
        this.items = list;
    }

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.FloorItemsMessageComposer);
        response.writeInt(this.items.size());
        for (Item floorItem : this.items) {
            response.writeInt(floorItem.getOwnerId());
            response.writeString(floorItem.getOwnerName());
        }

        response.writeInt(this.items.size());
        for (Item floorItem : this.items) {
            response.writeInt(floorItem.getId());
            response.writeInt(floorItem.getDefinition().getSpriteId());
            response.writeInt(floorItem.getPosition().getX());
            response.writeInt(floorItem.getPosition().getY());
            response.writeInt(floorItem.getPosition().getRotation());
            response.writeString("" + Util.format(floorItem.getPosition().getZ()));
            response.writeString("");
            ItemUtil.generateExtraData(floorItem, response);
            response.writeInt(-1);
            response.writeInt(floorItem.getDefinition().getInteractionModes() > 0 ? 1 : 0);
            response.writeInt(floorItem.getOwnerId());
        }
    }

    @Override
    public short getHeader() {
        return Outgoing.FloorItemsMessageComposer;
    }
}
