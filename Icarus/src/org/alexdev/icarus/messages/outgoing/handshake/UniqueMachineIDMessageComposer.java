package org.alexdev.icarus.messages.outgoing.handshake;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.messages.AbstractResponse;

public class UniqueMachineIDMessageComposer implements OutgoingMessageComposer {

	private String uniqueMachineId;

	public UniqueMachineIDMessageComposer(String uniqueMachineId) {
		this.uniqueMachineId = uniqueMachineId;
	}

	@Override
	public void write(AbstractResponse response) {
		
		if (this.uniqueMachineId == null) {
			return;
		}
		
		response.init(Outgoing.UniqueMachineIDMessageComposer);
		response.writeString(this.uniqueMachineId);
	}
}
