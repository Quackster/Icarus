package org.alexdev.icarus.messages.outgoing.room.notify;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.api.messages.AbstractResponse;

public class AcceptUserInsideRoomMessageComposer implements OutgoingMessageComposer {

	@Override
	public void write(AbstractResponse response) {
		response.init(Outgoing.AcceptUserInsideRoomMessageComposer);
		response.writeInt(1);
	}
}
