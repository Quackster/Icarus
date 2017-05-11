package org.alexdev.icarus.messages.outgoing.room.notify;

import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class RoomSettingsUpdatedMessageComposer implements OutgoingMessageComposer {

	private int roomId;

	public RoomSettingsUpdatedMessageComposer(int roomId) {
		this.roomId = roomId;
	}

	@Override
	public void write(Response response) {
		response.init(-1);//Outgoing.RoomSettingsUpdatedMessageComposer);
		response.writeInt(this.roomId);
	}

}