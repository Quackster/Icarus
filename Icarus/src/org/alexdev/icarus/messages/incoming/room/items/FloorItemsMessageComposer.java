package org.alexdev.icarus.messages.incoming.room.items;

import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.messages.AbstractResponse;

public class FloorItemsMessageComposer implements OutgoingMessageComposer {

	private Item[] items;

	public FloorItemsMessageComposer(Item[] items) {
		this.items = items;
	}

	@Override
	public void write(AbstractResponse response) {
		
		response.init(Outgoing.FloorItemsMessageComposer);
		response.appendInt32(items.length);
		
		for (Item floorItem : items) { 
			response.appendInt32(floorItem.getOwnerId());
			response.appendString(floorItem.getOwnerData().getUsername());
		}

		response.appendInt32(items.length);

		for (Item floorItem : items) {
			floorItem.serialise(response);
		}
	}
}
