package org.alexdev.icarus.messages.outgoing.room.notify;

import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class RoomSettingsOKMessageComposer extends OutgoingMessageComposer {

	private Room room;

	public RoomSettingsOKMessageComposer(Room room) {
		this.room = room;
	}

	@Override
	public void write() {
		response.init(-1);////Outgoing.RoomSettingsOKMessageComposer);
		response.writeInt(this.room.getData().getId());
	}

}
