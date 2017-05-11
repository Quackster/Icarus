package org.alexdev.icarus.messages.parsers;

import org.alexdev.icarus.server.api.messages.ClientMessage;

public interface IncomingMessageParser {
	public void read(ClientMessage reader);
}
