package org.alexdev.icarus.messages.outgoing.user;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;

public class HomeRoomMessageComposer extends OutgoingMessageComposer {

	private int roomId;
	private boolean forceEnter;

	public HomeRoomMessageComposer(int roomId, boolean forceEnter) {
		this.roomId = roomId;
		this.forceEnter = forceEnter;
	}

	@Override
	public void write() {
		response.init(Outgoing.HomeRoomMessageComposer);
        response.writeInt(this.roomId); 
        response.writeInt(this.forceEnter); 
	}

}
