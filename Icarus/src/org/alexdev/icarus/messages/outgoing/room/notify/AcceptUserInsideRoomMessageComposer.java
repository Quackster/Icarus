package org.alexdev.icarus.messages.outgoing.room.notify;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.MessageComposer;

public class AcceptUserInsideRoomMessageComposer extends MessageComposer {

    @Override
    public void write() {
        response.init(Outgoing.AcceptUserInsideRoomMessageComposer);
        response.writeInt(1);
    }
}
