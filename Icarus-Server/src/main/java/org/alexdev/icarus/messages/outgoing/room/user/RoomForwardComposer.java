package org.alexdev.icarus.messages.outgoing.room.user;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class RoomForwardComposer extends MessageComposer {

    private int roomId;

    public RoomForwardComposer(int roomId) {
        this.roomId = roomId;
    }

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.RoomForwardComposer);
        response.writeInt(this.roomId);
    }

    @Override
    public short getHeader() {
        return Outgoing.RoomForwardComposer;
    }
}