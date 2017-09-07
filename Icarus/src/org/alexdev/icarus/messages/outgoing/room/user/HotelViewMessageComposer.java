package org.alexdev.icarus.messages.outgoing.room.user;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.MessageComposer;

public class HotelViewMessageComposer extends MessageComposer {

    @Override
    public void write() {
        this.response.init(Outgoing.HotelScreenMessageComposer);
        this.response.writeInt(3);
    }
}
