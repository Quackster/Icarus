package org.alexdev.icarus.messages.outgoing.item;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;

public class RemoveInventoryItemComposer extends OutgoingMessageComposer {

	private int itemId;

	public RemoveInventoryItemComposer(int gameId) {
		this.itemId = gameId;
	}

	@Override
	public void write() {
		response.init(Outgoing.RemoveInventoryItemComposer);
		response.writeInt(this.itemId);
	}

}
