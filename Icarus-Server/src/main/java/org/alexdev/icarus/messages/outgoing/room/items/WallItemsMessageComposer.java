package org.alexdev.icarus.messages.outgoing.room.items;

import java.util.List;

import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.util.ItemUtil;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class WallItemsMessageComposer extends MessageComposer {

    private List<Item> items;

    public WallItemsMessageComposer(List<Item> list) {
        this.items = list;
    }

    @Override
    public void compose(Response response) {

        response.writeInt(this.items.size());
        for (Item wallItem : this.items) {
            response.writeInt(wallItem.getUserId());
            response.writeString(wallItem.getOwnerName());
        }

        response.writeInt(this.items.size());
        for (Item wallItem : this.items) {
            response.writeString(wallItem.getId() + "");
            response.writeInt(wallItem.getDefinition().getSpriteId());
            response.writeString(wallItem.getWallPosition());
            ItemUtil.generateWallExtraData(wallItem, response);
            response.writeInt(-1);
            response.writeInt(wallItem.getDefinition().getInteractionModes() > 0 ? 1 : 0);
            response.writeInt(wallItem.getUserId());
        }
    }

    @Override
    public short getHeader() {
        return Outgoing.WallItemsMessageComposer;
    }
}
