package org.alexdev.icarus.messages.outgoing.room.user;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.messages.AbstractResponse;

public class HotelViewMessageComposer implements OutgoingMessageComposer {

	@Override
	public void write(AbstractResponse response) {
		response.init(Outgoing.HotelScreenMessageComposer);
		response.appendInt32(3);
	}
}
