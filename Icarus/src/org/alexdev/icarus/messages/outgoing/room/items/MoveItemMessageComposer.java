package org.alexdev.icarus.messages.outgoing.room.items;

import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.item.ItemMetaDataUtil;
import org.alexdev.icarus.game.item.ItemType;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.MessageComposer;
import org.alexdev.icarus.util.Util;

public class MoveItemMessageComposer extends MessageComposer {

	private Item item;

	public MoveItemMessageComposer(Item item) {
		this.item = item;
	}

	@Override
	public void write() {

		if (this.item.getType() == ItemType.FLOOR) {
			this.response.init(Outgoing.MoveFloorItemMessageComposer); 
			this.response.writeInt(this.item.getId());
			this.response.writeInt(this.item.getDefinition().getSpriteId());
			this.response.writeInt(this.item.getPosition().getX());
			this.response.writeInt(this.item.getPosition().getY());
			this.response.writeInt(this.item.getPosition().getRotation());
			this.response.writeString("" + Util.getDecimalFormatter().format(item.getPosition().getZ()));
			this.response.writeString("");

			ItemMetaDataUtil.generateExtraData(item, response);

			this.response.writeInt(-1);
			this.response.writeInt(this.item.getDefinition().getInterationModes() > 0 ? 1 : 0);
			this.response.writeInt(this.item.getOwnerData().getId());
			this.response.writeString(this.item.getOwnerData().getUsername());
		}

		if (this.item.getType() == ItemType.WALL) {
			this.response.init(Outgoing.MoveWallItemMessageComposer);
			this.response.writeString(item.getId() + "");
			this.response.writeInt(item.getDefinition().getSpriteId());
			this.response.writeString(item.getWallPosition());
			this.response.writeString(item.getExtraData());
			this.response.writeInt(-1);
			this.response.writeInt(item.getDefinition().getInterationModes() > 0 ? 1 : 0);
			this.response.writeInt(item.getUserId());
			this.response.writeString(this.item.getOwnerData().getUsername());
		}
	}

}
