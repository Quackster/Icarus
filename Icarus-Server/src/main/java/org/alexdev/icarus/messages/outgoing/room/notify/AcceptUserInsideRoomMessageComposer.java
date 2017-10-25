package org.alexdev.icarus.messages.outgoing.room.notify;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class AcceptUserInsideRoomMessageComposer extends MessageComposer {

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.AcceptUserInsideRoomMessageComposer);
        response.writeInt(1);
    }

    @Override
    public short getHeader() {
        return Outgoing.AcceptUserInsideRoomMessageComposer;
    }
}
