package org.alexdev.icarus.messages.outgoing.room.items;

import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.ItemType;
import org.alexdev.icarus.game.util.ItemUtil;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;
import org.alexdev.icarus.util.Util;

public class PlaceItemMessageComposer extends MessageComposer {

    private Item item;

    public PlaceItemMessageComposer(Item item) {
        this.item = item;
    }

    @Override
    public void compose(Response response) {
        if (this.item.getDefinition().getType() == ItemType.FLOOR) {
            response.writeInt(this.item.getId());
            response.writeInt(this.item.getDefinition().getSpriteId());
            response.writeInt(this.item.getPosition().getX());
            response.writeInt(this.item.getPosition().getY());
            response.writeInt(this.item.getPosition().getRotation());
            response.writeString("" + Util.format(item.getPosition().getZ()));
            response.writeString("");

            ItemUtil.generateExtraData(item, response);

            response.writeInt(-1);
            response.writeInt(this.item.getDefinition().getInteractionModes() > 0 ? 1 : 0);
            response.writeInt(this.item.getUserId());
            response.writeString(this.item.getOwnerName());

        }

        if (this.item.getDefinition().getType() == ItemType.WALL) {
            response.writeString(item.getId() + "");
            response.writeInt(item.getDefinition().getSpriteId());
            response.writeString(item.getWallPosition());
            ItemUtil.generateWallExtraData(item, response);
            response.writeInt(-1);
            response.writeInt(item.getDefinition().getInteractionModes() > 0 ? 1 : 0);
            response.writeInt(this.item.getUserId());
            response.writeString(this.item.getOwnerName());
        }
    }

    @Override
    public short getHeader() {
        if (this.item.getDefinition().getType() == ItemType.FLOOR) {
            return Outgoing.PlaceFloorItemMessageComposer;
        }

        if (this.item.getDefinition().getType() == ItemType.WALL) {
            return Outgoing.PlaceWallItemMessageComposer;
        }

        return -1;
    }
}