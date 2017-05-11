package org.alexdev.icarus.messages.outgoing.room.notify;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class RoomEnterErrorMessageComposer extends OutgoingMessageComposer {
	
	private int errorCode;

	public RoomEnterErrorMessageComposer(int errorCode) {
		this.errorCode = errorCode;
	}

	@Override
	public void write() {
		response.init(Outgoing.RoomEnterErrorMessageComposer);
		response.writeInt(this.errorCode);

	}
}
