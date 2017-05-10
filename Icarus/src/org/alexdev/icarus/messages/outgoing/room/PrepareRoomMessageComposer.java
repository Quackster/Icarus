package org.alexdev.icarus.messages.outgoing.room;

import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.messages.AbstractResponse;

public class PrepareRoomMessageComposer implements OutgoingMessageComposer {

	private Room room;

	public PrepareRoomMessageComposer(Room room) {
		this.room = room;
	}

	@Override
	public void write(AbstractResponse response) {
		response.init(Outgoing.RoomUpdateMessageComposer);
		response.appendInt32(room.getData().getId());
	}
}
