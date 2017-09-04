package org.alexdev.icarus.messages.outgoing.room.notify;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.MessageComposer;

public class RoomEnterErrorMessageComposer extends MessageComposer {
    
    private int errorCode;

    public RoomEnterErrorMessageComposer(int errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public void write() {
        response.init(Outgoing.RoomEnterErrorMessageComposer);
        response.writeInt(this.errorCode);

    }
}
