package org.alexdev.icarus.messages.outgoing.room.notify;

import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.messages.AbstractResponse;

public class RoomSettingsOKMessageComposer implements OutgoingMessageComposer {

	private Room room;

	public RoomSettingsOKMessageComposer(Room room) {
		this.room = room;
	}

	@Override
	public void write(AbstractResponse response) {
		response.init(Outgoing.RoomSettingsOKMessageComposer);
		response.appendInt32(this.room.getData().getId());
	}

}
