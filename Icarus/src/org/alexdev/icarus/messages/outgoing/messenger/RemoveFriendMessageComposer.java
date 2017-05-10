package org.alexdev.icarus.messages.outgoing.messenger;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.messages.AbstractResponse;

public class RemoveFriendMessageComposer implements OutgoingMessageComposer {

	private int friendId;

	public RemoveFriendMessageComposer(int friendId) {
		this.friendId = friendId;
	}

	@Override
	public void write(AbstractResponse response) {

		response.init(Outgoing.RemoveFriendMessageComposer);
		response.appendInt32(0);
		response.appendInt32(1);
		response.appendInt32(-1);
		response.appendInt32(this.friendId);
	}

}
