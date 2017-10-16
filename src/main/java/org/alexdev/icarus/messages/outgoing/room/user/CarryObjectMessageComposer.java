package org.alexdev.icarus.messages.outgoing.room.user;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;

public class CarryObjectMessageComposer extends MessageComposer {

    private int virtualId;
    private int carryId;
    
    public CarryObjectMessageComposer(int virtualId, int carryId) {
        this.virtualId = virtualId;
        this.carryId = carryId;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.CarryObjectComposer);
        this.response.writeInt(this.virtualId);
        this.response.writeInt(this.carryId);
    }
}
