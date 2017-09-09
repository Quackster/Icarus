package org.alexdev.icarus.messages.outgoing.room.user;

import org.alexdev.icarus.messages.MessageComposer;
import org.alexdev.icarus.messages.headers.Outgoing;

public class CarryObjectComposer extends MessageComposer {

    private int virtualID;
    private int carryID;
    
    public CarryObjectComposer(int virtualID, int carryID) {
        this.virtualID = virtualID;
        this.carryID = carryID;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.CarryObjectComposer);
        this.response.writeInt(this.virtualID);
        this.response.writeInt(this.carryID);
    }
}
