package org.alexdev.icarus.messages.outgoing.room;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.api.messages.AbstractResponse;

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
		response.writeString(this.model);
		response.writeInt(this.id);
	}
}
