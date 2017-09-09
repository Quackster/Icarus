package org.alexdev.icarus.messages.outgoing.room.user;

import org.alexdev.icarus.messages.MessageComposer;
import org.alexdev.icarus.messages.headers.Outgoing;

public class RoomForwardComposer extends MessageComposer {

    private int roomID;

    public RoomForwardComposer(int roomID) {
        this.roomID = roomID;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.RoomForwardComposer);
        this.response.writeInt(this.roomID);
    }
}
