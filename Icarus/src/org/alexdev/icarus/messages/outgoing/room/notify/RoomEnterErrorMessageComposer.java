package org.alexdev.icarus.messages.outgoing.room.notify;

import org.alexdev.icarus.messages.MessageComposer;
import org.alexdev.icarus.messages.headers.Outgoing;

public class RoomEnterErrorMessageComposer extends MessageComposer {
    
    private int errorCode;

    public RoomEnterErrorMessageComposer(int errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.RoomEnterErrorMessageComposer);
        this.response.writeInt(this.errorCode);

    }
}
