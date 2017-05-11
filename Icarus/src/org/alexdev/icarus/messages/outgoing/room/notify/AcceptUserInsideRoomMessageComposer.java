package org.alexdev.icarus.messages.outgoing.room.notify;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class AcceptUserInsideRoomMessageComposer implements OutgoingMessageComposer {

	@Override
	public void write(Response response) {
		response.init(Outgoing.AcceptUserInsideRoomMessageComposer);
		response.writeInt(1);
	}
}
