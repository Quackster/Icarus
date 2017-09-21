package org.alexdev.icarus.messages.outgoing.room.notify;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;

public class AcceptUserInsideRoomMessageComposer extends MessageComposer {

    @Override
    public void write() {
        this.response.init(Outgoing.AcceptUserInsideRoomMessageComposer);
        this.response.writeInt(1);
    }
}
