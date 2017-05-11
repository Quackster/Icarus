package org.alexdev.icarus.messages.outgoing.item;

import java.util.List;

import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;

public class NewInventoryItemsMessageComposer extends OutgoingMessageComposer {

	private List<Item> items;

	public NewInventoryItemsMessageComposer(List<Item> items) {
		this.items = items;
	}

	@Override
	public void write() {
		
		response.init(Outgoing.FurniListNotificationComposer);
		response.writeInt(1);
		response.writeInt(items.size());
		
		for (Item bought : items) {	
			response.writeInt(1);
			response.writeInt(bought.getGameId());
		}
		
	}
}
