package org.alexdev.icarus.messages.outgoing.room.floorplan;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.MessageComposer;

public class FloorPlanSendDoorComposer extends MessageComposer {

	private int x;
	private int y;
	private int rotation;

	public FloorPlanSendDoorComposer(int x, int y, int rotation) {
		this.x = x;
		this.y = y;
		this.rotation = rotation;
	}

	@Override
	public void write() {
		this.response.init(Outgoing.FloorPlanSendDoorMessageComposer);
		this.response.writeInt(this.x);
		this.response.writeInt(this.y);
		this.response.writeInt(this.rotation);

	}

}
