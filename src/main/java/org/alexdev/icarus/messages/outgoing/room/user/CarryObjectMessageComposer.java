package org.alexdev.icarus.messages.outgoing.room.user;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class CarryObjectMessageComposer extends MessageComposer {

    private int virtualId;
    private int carryId;

    public CarryObjectMessageComposer(int virtualId, int carryId) {
        this.virtualId = virtualId;
        this.carryId = carryId;
    }

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.CarryObjectMessageComposer);
        response.writeInt(this.virtualId);
        response.writeInt(this.carryId);
    }

    @Override
    public short getHeader() {
        return Outgoing.CarryObjectMessageComposer;
    }
}