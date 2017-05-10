package org.alexdev.icarus.messages.outgoing.room;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.messages.AbstractResponse;

public class RoomModelMessageComposer implements OutgoingMessageComposer {
	
	private String model;
	private int id;

	public RoomModelMessageComposer(String model, int id) {
		this.model = model;
		this.id = id;
	}

	@Override
	public void write(AbstractResponse response) {
		response.init(Outgoing.InitialRoomInfoMessageComposer);
		response.appendString(this.model);
		response.appendInt32(this.id);
	}
}
