package org.alexdev.icarus.messages.outgoing.room;

import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.messages.AbstractResponse;

public class FloorMapMessageComposer implements OutgoingMessageComposer {

	private Room room;

	public FloorMapMessageComposer(Room room) {
		this.room = room;
	}

	@Override
	public void write(AbstractResponse response) {
		response.init(Outgoing.FloorMapMessageComposer);
		response.appendBoolean(true);
		response.appendInt32(this.room.getData().getWallHeight());
		response.appendString(this.room.getData().getModel().getFloorMap());
	}

}
