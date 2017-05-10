package org.alexdev.icarus.messages.outgoing.messenger;

import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.messages.AbstractResponse;

public class MessengerMessageComposer implements OutgoingMessageComposer {

	private int userId;
	private String message;

	public MessengerMessageComposer(int userId, String message) {
		this.userId = userId;
		this.message = message;
	}

	@Override
	public void write(AbstractResponse response) {
        response.appendInt32(this.userId);
        response.appendString(this.message);
        response.appendInt32(0);
	}

}
