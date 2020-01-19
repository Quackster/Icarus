package org.alexdev.icarus.messages.outgoing.room;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class RoomEnterComposer extends MessageComposer {
    @Override
    public void compose(Response response) {

    }

    @Override
    public short getHeader() {
        return Outgoing.RoomEnterComposer;
    }
}
