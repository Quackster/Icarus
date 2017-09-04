package org.alexdev.icarus.messages.outgoing.room.items;

import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.ItemMetaDataUtil;
import org.alexdev.icarus.game.item.ItemType;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.MessageComposer;
import org.alexdev.icarus.util.Util;

public class PlaceItemMessageComposer extends MessageComposer {

	private Item item;

	public PlaceItemMessageComposer(Item item) {
		this.item = item;
	}

	@Override
	public void write() {

		if (this.item.getType() == ItemType.FLOOR) {
			response.init(Outgoing.PlaceFloorItemMessageComposer); 

			response.writeInt(this.item.getId());
			response.writeInt(this.item.getDefinition().getSpriteId());
			response.writeInt(this.item.getPosition().getX());
			response.writeInt(this.item.getPosition().getY());
			response.writeInt(this.item.getPosition().getRotation());
			response.writeString("" + Util.getDecimalFormatter().format(item.getPosition().getZ()));
			response.writeString("");

			ItemMetaDataUtil.generateExtraData(item, response);

			response.writeInt(-1);
			response.writeInt(this.item.getDefinition().getInterationModes() > 0 ? 1 : 0);
			response.writeInt(this.item.getOwnerData().getId());
			response.writeString(this.item.getOwnerData().getUsername());

		}

		if (this.item.getType() == ItemType.WALL) {
			response.init(Outgoing.PlaceWallItemMessageComposer);
            response.writeString(item.getId() + "");
            response.writeInt(item.getDefinition().getSpriteId());
            response.writeString(item.getWallPosition());
            response.writeString(item.getExtraData());
            response.writeInt(-1);
            response.writeInt(item.getDefinition().getInterationModes() > 0 ? 1 : 0);
            response.writeInt(item.getUserId());
            response.writeString(this.item.getOwnerData().getUsername());
		}
	}

}
