package org.alexdev.icarus.messages.outgoing.room.notify;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class GenericNoAnswerDoorbellMessageComposer implements OutgoingMessageComposer {

	@Override
	public void write(Response response) {
		response.init(Outgoing.GenericNoAnswerDoorbellMessageComposer);
		response.writeString("");
	}
}
