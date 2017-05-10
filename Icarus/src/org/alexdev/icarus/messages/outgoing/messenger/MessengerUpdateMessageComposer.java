package org.alexdev.icarus.messages.outgoing.messenger;

import org.alexdev.icarus.game.messenger.MessengerUser;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.messages.AbstractResponse;

public class MessengerUpdateMessageComposer implements OutgoingMessageComposer {

	private MessengerUser friend;
	private boolean forceOffline;

	public MessengerUpdateMessageComposer(MessengerUser friend, boolean forceOffline) {
		this.friend = friend;
		this.forceOffline = forceOffline;
	}

	@Override
	public void write(AbstractResponse response) {
		
		response.init(Outgoing.FriendUpdateMessageComposer);
        response.appendInt32(0);
        response.appendInt32(1);
        response.appendInt32(0);
        this.friend.serialise(response, this.forceOffline);
        response.appendBoolean(false);
		
	}

}
