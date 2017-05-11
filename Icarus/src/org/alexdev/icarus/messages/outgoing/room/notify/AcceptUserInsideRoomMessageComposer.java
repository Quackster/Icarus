package org.alexdev.icarus.messages.outgoing.room.notify;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;

public class AcceptUserInsideRoomMessageComposer extends OutgoingMessageComposer {

	@Override
	public void write() {
		response.init(Outgoing.AcceptUserInsideRoomMessageComposer);
		response.writeInt(1);
	}
}
