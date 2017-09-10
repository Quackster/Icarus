package org.alexdev.icarus.messages.outgoing.room.notify;

import org.alexdev.icarus.messages.MessageComposer;
import org.alexdev.icarus.messages.headers.Outgoing;

public class RoomInfoUpdatedMessageComposer extends MessageComposer {

    private int roomId;

    public RoomInfoUpdatedMessageComposer(int roomId) {
        this.roomId = roomId;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.RoomInfoUpdatedMessageComposer);
        this.response.writeInt(this.roomId);
    }
}
