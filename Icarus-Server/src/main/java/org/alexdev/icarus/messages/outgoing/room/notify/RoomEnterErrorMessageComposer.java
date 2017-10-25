package org.alexdev.icarus.messages.outgoing.room.notify;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class RoomEnterErrorMessageComposer extends MessageComposer {

    private int errorCode;

    public RoomEnterErrorMessageComposer(int errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.RoomEnterErrorMessageComposer);
        response.writeInt(this.errorCode);

    }

    @Override
    public short getHeader() {
        return Outgoing.RoomEnterErrorMessageComposer;
    }
}