package org.alexdev.icarus.messages.outgoing.messenger;

import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class MessengerMessageComposer implements OutgoingMessageComposer {

	private int userId;
	private String message;

	public MessengerMessageComposer(int userId, String message) {
		this.userId = userId;
		this.message = message;
	}

	@Override
	public void write(Response response) {
        response.writeInt(this.userId);
        response.writeString(this.message);
        response.writeInt(0);
	}

}