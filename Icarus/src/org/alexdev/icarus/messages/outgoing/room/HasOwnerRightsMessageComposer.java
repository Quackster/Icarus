package org.alexdev.icarus.messages.outgoing.room;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.api.messages.AbstractResponse;

public class HasOwnerRightsMessageComposer implements OutgoingMessageComposer {

	@Override
	public void write(AbstractResponse response) {
		response.init(Outgoing.HasOwnerRightsMessageComposer);
	}
}
