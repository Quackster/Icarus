package org.alexdev.icarus.messages.outgoing.room.notify;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.messages.AbstractResponse;

public class GenericNoAnswerDoorbellMessageComposer implements OutgoingMessageComposer {

	@Override
	public void write(AbstractResponse response) {
		response.init(Outgoing.GenericNoAnswerDoorbellMessageComposer);
		response.writeString("");
	}
}
