package org.alexdev.icarus.messages.outgoing.room.floorplan;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class FloorPlanVisualMessagesComposer extends MessageComposer {

    private int wallThickness;
    private int floorThickness;
    private boolean hideWall;

    public FloorPlanVisualMessagesComposer(int wallThickness, int floorThickness, boolean hideWall) {
        this.wallThickness = wallThickness;
        this.floorThickness = floorThickness;
        this.hideWall = hideWall;
    }

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.FloorPlanVisualMessagesComposer);
        response.writeBool(this.hideWall);
        response.writeInt(this.wallThickness);
        response.writeInt(this.floorThickness);
    }

    @Override
    public short getHeader() {
        return Outgoing.FloorPlanVisualMessagesComposer;
    }
}