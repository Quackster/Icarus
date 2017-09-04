package org.alexdev.icarus.messages.outgoing.room.floorplan;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.MessageComposer;

public class FloorPlanRoomVisualComposer extends MessageComposer {

	private int wallThickness;
	private int floorThickness;
	private boolean hideWall;
	
	public FloorPlanRoomVisualComposer(int wallThickness, int floorThickness, boolean hideWall) {
		this.wallThickness = wallThickness;
		this.floorThickness = floorThickness;
		this.hideWall = hideWall;
	}

	@Override
	public void write() {
		this.response.init(Outgoing.FloorPlanRoomVisualComposer);
		this.response.writeBool(this.hideWall);
		this.response.writeInt(this.wallThickness);
		this.response.writeInt(this.floorThickness);
	}

}
