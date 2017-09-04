package org.alexdev.icarus.messages.outgoing.room.user;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.MessageComposer;

public class CarryObjectComposer extends MessageComposer {

    private int virtualId;
    private int carryId;
    
    public CarryObjectComposer(int virtualId, int carryId) {
        this.virtualId = virtualId;
        this.carryId = carryId;
    }

    @Override
    public void write() {
        response.init(Outgoing.CarryObjectComposer);
        response.writeInt(this.virtualId);
        response.writeInt(this.carryId);
    }

}
