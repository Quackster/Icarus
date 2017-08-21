package org.alexdev.icarus.messages.outgoing.catalogue;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;

public class PresentDeliverErrorMessageComposer extends OutgoingMessageComposer {

	private boolean creditsError;
	private boolean ducketError;

	public PresentDeliverErrorMessageComposer(boolean creditsError, boolean ducketError) {
		this.creditsError = creditsError;
		this.ducketError = ducketError;
	}

	@Override
	public void write() {
		response.init(Outgoing.PresentDeliverErrorMessageComposer);
	}

}
