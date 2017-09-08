package org.alexdev.icarus.messages.outgoing.room.notify;

import org.alexdev.icarus.messages.MessageComposer;
import org.alexdev.icarus.messages.headers.Outgoing;

public class AcceptUserInsideRoomMessageComposer extends MessageComposer {

    @Override
    public void write() {
        this.response.init(Outgoing.AcceptUserInsideRoomMessageComposer);
        this.response.writeInt(1);
    }
}
