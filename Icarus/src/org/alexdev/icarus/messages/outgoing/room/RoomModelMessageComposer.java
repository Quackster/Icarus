package org.alexdev.icarus.messages.outgoing.room;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class RoomModelMessageComposer implements OutgoingMessageComposer {
	
	private String model;
	private int id;

	public RoomModelMessageComposer(String model, int id) {
		this.model = model;
		this.id = id;
	}

	@Override
	public void write(Response response) {
		response.init(Outgoing.InitialRoomInfoMessageComposer);
		response.writeString(this.model);
		response.writeInt(this.id);
	}
}
