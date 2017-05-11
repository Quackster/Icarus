package org.alexdev.icarus.messages.outgoing.room.user;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class RemoveUserMessageComposer implements OutgoingMessageComposer {

	private int virtualId;

	public RemoveUserMessageComposer(int virtualId) {
		this.virtualId = virtualId;
	}

	@Override
	public void write(Response response) {
		response.init(Outgoing.UserLeftRoomMessageComposer);
		response.writeString(this.virtualId + "");
	}

}
