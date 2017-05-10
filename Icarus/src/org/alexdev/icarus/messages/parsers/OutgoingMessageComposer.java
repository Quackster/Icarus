package org.alexdev.icarus.messages.parsers;

import org.alexdev.icarus.server.messages.AbstractResponse;

public interface OutgoingMessageComposer {
	public void write(AbstractResponse response);
}
