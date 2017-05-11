package org.alexdev.icarus.messages.outgoing.catalogue;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class PurchaseErrorMessageComposer implements OutgoingMessageComposer {

	private boolean creditsError;
	private boolean pixelError;

	public PurchaseErrorMessageComposer(boolean creditsError, boolean pixelError) {
		this.creditsError = creditsError;
		this.pixelError = pixelError;
	}

	@Override
	public void write(Response response) {
		
		response.init(Outgoing.LackFundsMessageComposer);
		response.writeBool(creditsError);
		response.writeBool(pixelError);
		
	}

}
