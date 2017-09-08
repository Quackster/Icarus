package org.alexdev.icarus.messages.outgoing.room.user.copy;

import org.alexdev.icarus.messages.MessageComposer;
import org.alexdev.icarus.messages.headers.Outgoing;

public class HotelViewMessageComposer extends MessageComposer {

    @Override
    public void write() {
        this.response.init(Outgoing.HotelScreenMessageComposer);
        this.response.writeInt(3);
    }
}
