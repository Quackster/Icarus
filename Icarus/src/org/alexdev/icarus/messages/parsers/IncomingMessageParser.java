package org.alexdev.icarus.messages.parsers;

import org.alexdev.icarus.server.messages.AbstractReader;

public interface IncomingMessageParser {
	public void read(AbstractReader reader);
}
