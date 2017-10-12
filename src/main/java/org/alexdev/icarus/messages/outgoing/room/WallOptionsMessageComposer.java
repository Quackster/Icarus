package org.alexdev.icarus.messages.outgoing.room;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;

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
    public void write() {
        this.response.init(Outgoing.WallOptionsMessageComposer);
        this.response.writeBool(this.hideWall);
        this.response.writeInt(this.wallThickness);
        this.response.writeInt(this.floorThickness);
    }
}
