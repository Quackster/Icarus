package org.alexdev.icarus.messages.outgoing.room.items;

import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.messages.AbstractResponse;

public class WallItemsMessageComposer implements OutgoingMessageComposer {

	private Item[] items;

	public WallItemsMessageComposer(Item[] items) {
		this.items = items;
	}

	@Override
	public void write(AbstractResponse response) {
		
		response.init(Outgoing.WallItemsMessageComposer);
		
		response.appendInt32(this.items.length);
		for (Item wallItem : this.items) { 
			response.appendInt32(wallItem.getOwnerId());
			response.appendString(wallItem.getOwnerData().getUsername());
		}

		response.appendInt32(this.items.length);
		for (Item wallItem : this.items) {
			wallItem.serialise(response);
		}
	}

}
