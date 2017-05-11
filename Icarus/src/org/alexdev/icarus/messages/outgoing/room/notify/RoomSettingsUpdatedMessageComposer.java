package org.alexdev.icarus.messages.outgoing.room.notify;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.messages.AbstractResponse;

public class RoomSettingsUpdatedMessageComposer implements OutgoingMessageComposer {

	private int roomId;

	public RoomSettingsUpdatedMessageComposer(int roomId) {
		this.roomId = roomId;
	}

	@Override
	public void write(AbstractResponse response) {
		response.init(Outgoing.RoomSettingsUpdatedMessageComposer);
		response.writeInt(this.roomId);
	}

}
