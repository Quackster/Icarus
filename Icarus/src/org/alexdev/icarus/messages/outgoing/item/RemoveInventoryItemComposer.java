package org.alexdev.icarus.messages.outgoing.item;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.messages.AbstractResponse;

public class RemoveInventoryItemComposer implements OutgoingMessageComposer {

	private int itemId;

	public RemoveInventoryItemComposer(int gameId) {
		this.itemId = gameId;
	}

	@Override
	public void write(AbstractResponse response) {
		response.init(Outgoing.RemoveInventoryItemComposer);
		response.writeInt(this.itemId);
	}

}
