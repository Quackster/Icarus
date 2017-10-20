package org.alexdev.icarus.messages.outgoing.room.user;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class HotelViewMessageComposer extends MessageComposer {

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.HotelViewMessageComposer);
        response.writeInt(3);
    }

    @Override
    public short getHeader() {
        return Outgoing.HotelViewMessageComposer;
    }
}
