package org.alexdev.icarus.messages.outgoing.room.floorplan;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

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
    public void compose(Response response) {
        //response.init(Outgoing.FloorPlanSendDoorMessageComposer);
        response.writeInt(this.x);
        response.writeInt(this.y);
        response.writeInt(this.rotation);

    }

    @Override
    public short getHeader() {
        return Outgoing.FloorPlanSendDoorMessageComposer;
    }
}