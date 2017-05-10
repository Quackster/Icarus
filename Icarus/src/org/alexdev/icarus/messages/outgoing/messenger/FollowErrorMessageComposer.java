package org.alexdev.icarus.messages.outgoing.messenger;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.messages.AbstractResponse;

public class FollowErrorMessageComposer implements OutgoingMessageComposer {

	private int errorID;

	public FollowErrorMessageComposer(int errorID) {
		this.errorID = errorID;
	}

	@Override
	public void write(AbstractResponse response) {
		response.init(Outgoing.FollowErrorMessageComposer);
        response.appendInt32(this.errorID);

	}

}
