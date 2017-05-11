package org.alexdev.icarus.messages.outgoing.user;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.messages.AbstractResponse;

public class CreditsBalanceMessageComposer implements OutgoingMessageComposer {

	private int credits;

	public CreditsBalanceMessageComposer(int credits) {
		this.credits = credits;
	}

	@Override
	public void write(AbstractResponse response) {
		response.init(Outgoing.CreditsBalanceMessageComposer);
		response.writeString(this.credits + ".0");
	}

}
