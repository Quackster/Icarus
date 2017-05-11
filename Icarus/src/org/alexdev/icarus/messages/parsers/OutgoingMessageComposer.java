package org.alexdev.icarus.messages.parsers;

import org.alexdev.icarus.server.api.messages.Response;

public interface OutgoingMessageComposer {
	public void write(Response response);
}
