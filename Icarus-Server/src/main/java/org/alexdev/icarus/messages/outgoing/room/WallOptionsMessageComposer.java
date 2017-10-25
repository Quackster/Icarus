package org.alexdev.icarus.messages.outgoing.room;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class WallOptionsMessageComposer extends MessageComposer {

    private boolean hideWall;
    private int wallThickness;
    private int floorThickness;

    public WallOptionsMessageComposer(boolean hideWall, int wallThickness, int floorThickness) {
        this.hideWall = hideWall;
        this.wallThickness = wallThickness;
        this.floorThickness = floorThickness;
    }

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.WallOptionsMessageComposer);
        response.writeBool(this.hideWall);
        response.writeInt(this.wallThickness);
        response.writeInt(this.floorThickness);
    }

    @Override
    public short getHeader() {
        return Outgoing.WallOptionsMessageComposer;
    }
}