package org.alexdev.icarus.messages.outgoing.room.notify;

import org.alexdev.icarus.messages.MessageComposer;
import org.alexdev.icarus.messages.headers.Outgoing;

public class RoomInfoUpdatedMessageComposer extends MessageComposer {

    private int roomID;

    public RoomInfoUpdatedMessageComposer(int roomID) {
        this.roomID = roomID;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.RoomInfoUpdatedMessageComposer);
        this.response.writeInt(this.roomID);
    }
}
