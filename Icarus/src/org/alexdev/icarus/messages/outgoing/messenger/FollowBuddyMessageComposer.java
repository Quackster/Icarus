package org.alexdev.icarus.messages.outgoing.messenger;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.messages.AbstractResponse;

public class FollowBuddyMessageComposer implements OutgoingMessageComposer {

	private int friendId;

	public FollowBuddyMessageComposer(int friendId) {
		this.friendId = friendId;
	}

	@Override
	public void write(AbstractResponse response) {
    	
    	response.init(Outgoing.FollowBuddyMessageComposer);
        response.writeInt(this.friendId);
	}

}
