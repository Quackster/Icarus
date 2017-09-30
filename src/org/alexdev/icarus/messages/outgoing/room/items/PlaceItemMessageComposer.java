package org.alexdev.icarus.messages.outgoing.room.items;

import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.ItemType;
import org.alexdev.icarus.game.util.ItemUtil;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.util.Util;

public class PlaceItemMessageComposer extends MessageComposer {

    private Item item;

    public PlaceItemMessageComposer(Item item) {
        this.item = item;
    }

    @Override
    public void write() {

        if (this.item.getDefinition().getType() == ItemType.FLOOR) {
            this.response.init(Outgoing.PlaceFloorItemMessageComposer); 

            this.response.writeInt(this.item.getId());
            this.response.writeInt(this.item.getDefinition().getSpriteId());
            this.response.writeInt(this.item.getPosition().getX());
            this.response.writeInt(this.item.getPosition().getY());
            this.response.writeInt(this.item.getPosition().getRotation());
            this.response.writeString("" + Util.format(item.getPosition().getZ()));
            this.response.writeString("");

            ItemUtil.generateExtraData(item, response);

            this.response.writeInt(-1);
            this.response.writeInt(this.item.getDefinition().getInteractionModes() > 0 ? 1 : 0);
            this.response.writeInt(this.item.getOwnerId());
            this.response.writeString(this.item.getOwnerName());

        }

        if (this.item.getDefinition().getType() == ItemType.WALL) {
            this.response.init(Outgoing.PlaceWallItemMessageComposer);
            this.response.writeString(item.getId() + "");
            this.response.writeInt(item.getDefinition().getSpriteId());
            this.response.writeString(item.getWallPosition());
            this.response.writeString(item.getExtraData());
            this.response.writeInt(-1);
            this.response.writeInt(item.getDefinition().getInteractionModes() > 0 ? 1 : 0);
            this.response.writeInt(this.item.getOwnerId());
            this.response.writeString(this.item.getOwnerName());
        }
    }
}
