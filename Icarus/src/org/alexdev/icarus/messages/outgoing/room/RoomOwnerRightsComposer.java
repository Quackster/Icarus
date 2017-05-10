package org.alexdev.icarus.messages.outgoing.room;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.messages.AbstractResponse;

public class RoomOwnerRightsComposer implements OutgoingMessageComposer {

	private int id;
	private boolean isOwner;

	public RoomOwnerRightsComposer(int id, boolean isOwner) {
		this.id = id;
		this.isOwner = isOwner;
	}

	@Override
	public void write(AbstractResponse response) {
		response.init(Outgoing.RoomOwnerRightsComposer);
		response.appendInt32(this.id);
		response.appendBoolean(this.isOwner);
	}

}
