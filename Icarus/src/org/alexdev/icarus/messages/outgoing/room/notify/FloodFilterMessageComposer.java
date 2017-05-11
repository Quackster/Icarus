package org.alexdev.icarus.messages.outgoing.room.notify;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class FloodFilterMessageComposer implements OutgoingMessageComposer {

	private int waitSeconds;

	public FloodFilterMessageComposer(int waitSeconds) {
		this.waitSeconds = waitSeconds;
	}

	@Override
	public void write(Response response) {
		response.init(Outgoing.FloodFilterMessageComposer);
		response.writeInt(this.waitSeconds);
	}

}
