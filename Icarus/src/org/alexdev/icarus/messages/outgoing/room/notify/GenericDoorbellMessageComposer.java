package org.alexdev.icarus.messages.outgoing.room.notify;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class GenericDoorbellMessageComposer extends OutgoingMessageComposer {

	private String username;
	private int notifyCode = -1;

	public GenericDoorbellMessageComposer(String username) {
		this.username = username;
	}

	public GenericDoorbellMessageComposer(int notifyCode) {
		this.notifyCode = notifyCode;
	}

	@Override
	public void write() {
		response.init(Outgoing.GenericDoorbellMessageComposer);

		if (this.username != null) {
			response.writeString(this.username); 
		} else {
			response.writeInt(this.notifyCode);
		}
	}
}
