package org.alexdev.icarus.messages.outgoing.item;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;

public class UpdateInventoryMessageComposer extends OutgoingMessageComposer {

	@Override
	public void write() {
		response.init(Outgoing.UpdateInventoryMessageComposer);
		
	}
}
