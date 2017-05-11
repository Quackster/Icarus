package org.alexdev.icarus.messages.outgoing.room;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class RoomSpacesMessageComposer implements OutgoingMessageComposer {

	private String space;
	private String data;

	public RoomSpacesMessageComposer(String space, String data) {
		this.space = space;
		this.data = data;
	}

	@Override
	public void write(Response response) {
		response.init(Outgoing.RoomSpacesMessageComposer);
		response.writeString(this.space);
		response.writeString(this.data);
	}

}
