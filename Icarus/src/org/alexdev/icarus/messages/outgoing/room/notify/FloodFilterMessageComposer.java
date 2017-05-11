package org.alexdev.icarus.messages.outgoing.room.notify;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;

public class FloodFilterMessageComposer extends OutgoingMessageComposer {

	private int waitSeconds;

	public FloodFilterMessageComposer(int waitSeconds) {
		this.waitSeconds = waitSeconds;
	}

	@Override
	public void write() {
		response.init(Outgoing.FloodFilterMessageComposer);
		response.writeInt(this.waitSeconds);
	}

}
