package org.alexdev.icarus.messages.outgoing.room;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;

public class HasOwnerRightsMessageComposer extends OutgoingMessageComposer {

	@Override
	public void write() {
		response.init(Outgoing.HasOwnerRightsMessageComposer);
	}
}
