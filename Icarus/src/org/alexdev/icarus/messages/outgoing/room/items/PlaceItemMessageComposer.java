package org.alexdev.icarus.messages.outgoing.room.items;

import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.ItemExtraDataUtil;
import org.alexdev.icarus.game.item.ItemType;
import org.alexdev.icarus.messages.MessageComposer;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.util.Util;

public class PlaceItemMessageComposer extends MessageComposer {

    private Item item;

    public PlaceItemMessageComposer(Item item) {
        this.item = item;
    }

    @Override
    public void write() {

        if (this.item.getType() == ItemType.FLOOR) {
            this.response.init(Outgoing.PlaceFloorItemMessageComposer); 

            this.response.writeInt(this.item.getID());
            this.response.writeInt(this.item.getDefinition().getSpriteID());
            this.response.writeInt(this.item.getPosition().getX());
            this.response.writeInt(this.item.getPosition().getY());
            this.response.writeInt(this.item.getPosition().getRotation());
            this.response.writeString("" + Util.getDecimalFormatter().format(item.getPosition().getZ()));
            this.response.writeString("");

            ItemExtraDataUtil.generateExtraData(item, response);

            this.response.writeInt(-1);
            this.response.writeInt(this.item.getDefinition().getInterationModes() > 0 ? 1 : 0);
            this.response.writeInt(this.item.getOwnerID());
            this.response.writeString(this.item.getOwnerName());

        }

        if (this.item.getType() == ItemType.WALL) {
            this.response.init(Outgoing.PlaceWallItemMessageComposer);
            this.response.writeString(item.getID() + "");
            this.response.writeInt(item.getDefinition().getSpriteID());
            this.response.writeString(item.getWallPosition());
            this.response.writeString(item.getExtraData());
            this.response.writeInt(-1);
            this.response.writeInt(item.getDefinition().getInterationModes() > 0 ? 1 : 0);
            this.response.writeInt(this.item.getOwnerID());
            this.response.writeString(this.item.getOwnerName());
        }
    }
}
