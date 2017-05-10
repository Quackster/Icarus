package org.alexdev.icarus.messages.outgoing.item;

import java.util.List;

import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.messages.AbstractResponse;

public class NewInventoryItemsMessageComposer implements OutgoingMessageComposer {

	private List<Item> items;

	public NewInventoryItemsMessageComposer(List<Item> items) {
		this.items = items;
	}

	@Override
	public void write(AbstractResponse response) {
		
		response.init(Outgoing.NewInventoryItemsMessageComposer);
		response.appendInt32(1);
		response.appendInt32(items.size());
		
		for (Item bought : items) {	
			response.appendInt32(1);
			response.appendInt32(bought.getGameId());
		}
		
	}
}
