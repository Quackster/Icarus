package org.alexdev.icarus.messages.outgoing.room;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class RoomRightsLevelMessageComposer implements OutgoingMessageComposer {

	private int status;

	public RoomRightsLevelMessageComposer(int status) {
		this.status = status;
	}

	@Override
	public void write(Response response) {
		response.init(Outgoing.RoomRightsLevelMessageComposer);
		response.writeInt(this.status);
	}

}
