package org.alexdev.icarus.messages.outgoing.room;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.messages.AbstractResponse;

public class RoomSpacesMessageComposer implements OutgoingMessageComposer {

	private String space;
	private String data;

	public RoomSpacesMessageComposer(String space, String data) {
		this.space = space;
		this.data = data;
	}

	@Override
	public void write(AbstractResponse response) {
		response.init(Outgoing.RoomSpacesMessageComposer);
		response.appendString(this.space);
		response.appendString(this.data);
	}

}
