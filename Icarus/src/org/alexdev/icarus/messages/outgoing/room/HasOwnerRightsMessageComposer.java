package org.alexdev.icarus.messages.outgoing.room;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class HasOwnerRightsMessageComposer implements OutgoingMessageComposer {

	@Override
	public void write(Response response) {
		response.init(Outgoing.HasOwnerRightsMessageComposer);
	}
}
