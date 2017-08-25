package org.alexdev.icarus.game.room;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;

public class RoomForwardComposer extends OutgoingMessageComposer {

	private int roomId;

	public RoomForwardComposer(int roomId) {
		this.roomId = roomId;
	}

	@Override
	public void write() {
		this.response.init(Outgoing.RoomForwardComposer);
		this.response.writeInt(this.roomId);
	}

}
