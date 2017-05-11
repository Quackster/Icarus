package org.alexdev.icarus.messages.outgoing.room.user;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;

public class HotelViewMessageComposer extends OutgoingMessageComposer {

	@Override
	public void write() {
		response.init(Outgoing.HotelScreenMessageComposer);
		response.writeInt(3);
	}
}
