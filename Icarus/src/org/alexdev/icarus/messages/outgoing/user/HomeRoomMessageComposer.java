package org.alexdev.icarus.messages.outgoing.user;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.api.messages.AbstractResponse;

public class HomeRoomMessageComposer implements OutgoingMessageComposer {

	private int roomId;
	private boolean forceEnter;

	public HomeRoomMessageComposer(int roomId, boolean forceEnter) {
		this.roomId = roomId;
		this.forceEnter = forceEnter;
	}

	@Override
	public void write(AbstractResponse response) {
		response.init(Outgoing.HomeRoomMessageComposer);
        response.writeInt(this.roomId); 
        response.appendInt32(this.forceEnter); 
	}

}
