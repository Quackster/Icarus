package org.alexdev.icarus.messages.outgoing.handshake;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.messages.AbstractResponse;

public class AuthenticationOKMessageComposer implements OutgoingMessageComposer {

	public void write(AbstractResponse response) {
		response.init(Outgoing.AuthenticationOKMessageComposer);
	}

}
