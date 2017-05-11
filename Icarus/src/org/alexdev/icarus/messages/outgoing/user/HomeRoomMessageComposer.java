package org.alexdev.icarus.messages.outgoing.user;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class HomeRoomMessageComposer implements OutgoingMessageComposer {

	private int roomId;
	private boolean forceEnter;

	public HomeRoomMessageComposer(int roomId, boolean forceEnter) {
		this.roomId = roomId;
		this.forceEnter = forceEnter;
	}

	@Override
	public void write(Response response) {
		response.init(Outgoing.HomeRoomMessageComposer);
        response.writeInt(this.roomId); 
        response.writeInt(this.forceEnter); 
	}

}
