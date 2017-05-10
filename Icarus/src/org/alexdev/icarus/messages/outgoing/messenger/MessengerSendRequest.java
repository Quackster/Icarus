package org.alexdev.icarus.messages.outgoing.messenger;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.messages.AbstractResponse;

public class MessengerSendRequest implements OutgoingMessageComposer {

	private int id;
	private String username;
	private String figure;

	public MessengerSendRequest(int id, String username, String figure) {
		this.id = id;
		this.username = username;
		this.figure = figure;
	}

	@Override
	public void write(AbstractResponse response) {
		response.init(Outgoing.MessengerSendRequest);
		response.appendInt32(this.id);
		response.appendString(this.username);
		response.appendString(this.figure);
	}

}
