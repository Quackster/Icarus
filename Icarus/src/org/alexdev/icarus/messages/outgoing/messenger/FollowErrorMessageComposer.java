package org.alexdev.icarus.messages.outgoing.messenger;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class FollowErrorMessageComposer extends OutgoingMessageComposer {

	private int errorID;

	public FollowErrorMessageComposer(int errorID) {
		this.errorID = errorID;
	}

	@Override
	public void write() {
		response.init(Outgoing.FollowErrorMessageComposer);
        response.writeInt(this.errorID);

	}

}
