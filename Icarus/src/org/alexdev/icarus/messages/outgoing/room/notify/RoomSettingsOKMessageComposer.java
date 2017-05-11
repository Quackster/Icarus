package org.alexdev.icarus.messages.outgoing.room.notify;

import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class RoomSettingsOKMessageComposer implements OutgoingMessageComposer {

	private Room room;

	public RoomSettingsOKMessageComposer(Room room) {
		this.room = room;
	}

	@Override
	public void write(Response response) {
		response.init(Outgoing.RoomSettingsOKMessageComposer);
		response.writeInt(this.room.getData().getId());
	}

}
