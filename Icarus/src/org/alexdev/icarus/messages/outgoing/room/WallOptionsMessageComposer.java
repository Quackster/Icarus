package org.alexdev.icarus.messages.outgoing.room;

import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

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
	public void write(Response response) {
		
		//response.init(Outgoing.WallOptionsMessageComposer);
	    response.init(-1);
		response.writeBool(this.hideWall);
		response.writeInt(this.wallThickness);
		response.writeInt(this.floorThickness);
	}
}
