package org.alexdev.icarus.messages.incoming.room.items;

import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.api.messages.AbstractResponse;

public class FloorItemsMessageComposer implements OutgoingMessageComposer {

	private Item[] items;

	public FloorItemsMessageComposer(Item[] items) {
		this.items = items;
	}

	@Override
	public void write(AbstractResponse response) {
		
		response.init(Outgoing.FloorItemsMessageComposer);
		response.writeInt(items.length);
		
		for (Item floorItem : items) { 
			response.writeInt(floorItem.getOwnerId());
			response.writeString(floorItem.getOwnerData().getUsername());
		}

		response.writeInt(items.length);

		for (Item floorItem : items) {
			floorItem.serialise(response);
		}
	}
}
