package org.alexdev.icarus.messages.outgoing.messenger;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class FollowBuddyMessageComposer implements OutgoingMessageComposer {

	private int friendId;

	public FollowBuddyMessageComposer(int friendId) {
		this.friendId = friendId;
	}

	@Override
	public void write(Response response) {
    	
    	response.init(Outgoing.FollowBuddyMessageComposer);
        response.writeInt(this.friendId);
	}

}
