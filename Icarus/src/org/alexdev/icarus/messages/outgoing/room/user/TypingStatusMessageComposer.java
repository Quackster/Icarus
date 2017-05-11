package org.alexdev.icarus.messages.outgoing.room.user;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.api.messages.AbstractResponse;

public class TypingStatusMessageComposer implements OutgoingMessageComposer {

	private int virtualId;
	private boolean typeStart;

	public TypingStatusMessageComposer(int virtualId, boolean typeStart) {
		this.virtualId = virtualId;
		this.typeStart = typeStart;
	}

	@Override
	public void write(AbstractResponse response) {
		response.init(Outgoing.TypingStatusMessageComposer);
		response.writeInt(this.virtualId);
		response.appendInt32(this.typeStart);
		
	}

}
