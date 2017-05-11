package org.alexdev.icarus.messages.outgoing.item;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class RemoveInventoryItemComposer implements OutgoingMessageComposer {

	private int itemId;

	public RemoveInventoryItemComposer(int gameId) {
		this.itemId = gameId;
	}

	@Override
	public void write(Response response) {
		response.init(Outgoing.RemoveInventoryItemComposer);
		response.writeInt(this.itemId);
	}

}
