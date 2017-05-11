package org.alexdev.icarus.messages.outgoing.room;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.api.messages.AbstractResponse;

public class WallOptionsMessageComposer implements OutgoingMessageComposer {

	private boolean hideWall;
	private int wallThickness;
	private int floorThickness;

	public WallOptionsMessageComposer(boolean hideWall, int wallThickness, int floorThickness) {
		this.hideWall = hideWall;
		this.wallThickness = wallThickness;
		this.floorThickness = floorThickness;
	}

	@Override
	public void write(AbstractResponse response) {
		
		response.init(Outgoing.WallOptionsMessageComposer);
		response.writeBool(this.hideWall);
		response.writeInt(this.wallThickness);
		response.writeInt(this.floorThickness);
	}
}
