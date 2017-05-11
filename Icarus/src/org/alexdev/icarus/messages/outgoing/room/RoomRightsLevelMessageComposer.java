package org.alexdev.icarus.messages.outgoing.room;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class RoomRightsLevelMessageComposer extends OutgoingMessageComposer {

	private int status;

	public RoomRightsLevelMessageComposer(int status) {
		this.status = status;
	}

	@Override
	public void write() {
		response.init(Outgoing.RightsLevelMessageComposer);
		response.writeInt(this.status);
	}

}
