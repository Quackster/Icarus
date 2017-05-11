package org.alexdev.icarus.messages.outgoing.item;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

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
