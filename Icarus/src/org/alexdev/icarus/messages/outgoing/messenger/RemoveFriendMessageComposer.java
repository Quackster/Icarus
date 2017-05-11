package org.alexdev.icarus.messages.outgoing.messenger;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.api.messages.AbstractResponse;

public class RemoveFriendMessageComposer implements OutgoingMessageComposer {

	private int friendId;

	public RemoveFriendMessageComposer(int friendId) {
		this.friendId = friendId;
	}

	@Override
	public void write(AbstractResponse response) {

		response.init(Outgoing.RemoveFriendMessageComposer);
		response.writeInt(0);
		response.writeInt(1);
		response.writeInt(-1);
		response.writeInt(this.friendId);
	}

}
