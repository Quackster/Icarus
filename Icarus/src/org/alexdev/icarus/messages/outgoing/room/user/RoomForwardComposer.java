package org.alexdev.icarus.messages.outgoing.room.user;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.MessageComposer;

public class RoomForwardComposer extends MessageComposer {

    private int roomId;

    public RoomForwardComposer(int roomId) {
        this.roomId = roomId;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.RoomForwardComposer);
        this.response.writeInt(this.roomId);
    }
}
