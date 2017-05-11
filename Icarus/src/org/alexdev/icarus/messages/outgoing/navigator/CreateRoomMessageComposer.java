package org.alexdev.icarus.messages.outgoing.navigator;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;

public class CreateRoomMessageComposer extends OutgoingMessageComposer {

	private int id;
	private String name;

	public CreateRoomMessageComposer(int id, String name) {
		this.id = id;
		this.name = name;
	}

	@Override
	public void write() {
		response.init(Outgoing.CreateRoomMessageComposer);
		response.writeInt(this.id);
		response.writeString(this.name);
	}

}
