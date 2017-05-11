package org.alexdev.icarus.messages.outgoing.room.items;

import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class WallItemsMessageComposer extends OutgoingMessageComposer {

	private Item[] items;

	public WallItemsMessageComposer(Item[] items) {
		this.items = items;
	}

	@Override
	public void write() {
		
		response.init(Outgoing.WallItemsMessageComposer);
		
		response.writeInt(this.items.length);
		for (Item wallItem : this.items) { 
			response.writeInt(wallItem.getOwnerId());
			response.writeString(wallItem.getOwnerData().getUsername());
		}

		response.writeInt(this.items.length);
		for (Item wallItem : this.items) {
			wallItem.serialise(response);
		}
	}

}
