package org.alexdev.icarus.messages.outgoing.user;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class LandingWidgetMessageComposer implements OutgoingMessageComposer {

	@Override
	public void write(Response response) {
		response.init(Outgoing.LandingWidgetMessageComposer);
		response.writeString("");
		response.writeString("");
	}
}
