package org.alexdev.icarus.messages.outgoing.room.notify;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.messages.AbstractResponse;

public class GenericDoorbellMessageComposer implements OutgoingMessageComposer {

	private String username;
	private int notifyCode = -1;

	public GenericDoorbellMessageComposer(String username) {
		this.username = username;
	}

	public GenericDoorbellMessageComposer(int notifyCode) {
		this.notifyCode = notifyCode;
	}

	@Override
	public void write(AbstractResponse response) {
		response.init(Outgoing.GenericDoorbellMessageComposer);

		if (this.username != null) {
			response.appendString(this.username); 
		} else {
			response.appendInt32(this.notifyCode);
		}
	}
}
