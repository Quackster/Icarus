package org.alexdev.icarus.messages.outgoing.room.notify;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class RoomInfoUpdatedMessageComposer extends MessageComposer {

    private int roomId;

    public RoomInfoUpdatedMessageComposer(int roomId) {
        this.roomId = roomId;
    }

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.RoomInfoUpdatedMessageComposer);
        response.writeInt(this.roomId);
    }

    @Override
    public short getHeader() {
        return Outgoing.RoomInfoUpdatedMessageComposer;
    }
}