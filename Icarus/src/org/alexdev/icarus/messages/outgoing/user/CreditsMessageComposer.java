package org.alexdev.icarus.messages.outgoing.user;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class CreditsMessageComposer implements OutgoingMessageComposer {

	private int credits;

	public CreditsMessageComposer(int credits) {
		this.credits = credits;
	}

	@Override
	public void write(Response response) {
		response.init(Outgoing.CreditsMessageComposer);
		response.writeString(this.credits + ".0");
	}

}
