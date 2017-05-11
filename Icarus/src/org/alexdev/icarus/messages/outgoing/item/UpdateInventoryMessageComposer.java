package org.alexdev.icarus.messages.outgoing.item;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.api.messages.AbstractResponse;

public class UpdateInventoryMessageComposer implements OutgoingMessageComposer {

	@Override
	public void write(AbstractResponse response) {
		response.init(Outgoing.UpdateInventoryMessageComposer);
		
	}
}
