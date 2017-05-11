package org.alexdev.icarus.messages.outgoing.handshake;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class UniqueMachineIDMessageComposer extends OutgoingMessageComposer {

	private String uniqueMachineId;

	public UniqueMachineIDMessageComposer(String uniqueMachineId) {
		this.uniqueMachineId = uniqueMachineId;
	}

	@Override
	public void write() {
		
		if (this.uniqueMachineId == null) {
			return;
		}
		
		response.init(Outgoing.UniqueMachineIDMessageComposer);
		response.writeString(this.uniqueMachineId);
	}
}
