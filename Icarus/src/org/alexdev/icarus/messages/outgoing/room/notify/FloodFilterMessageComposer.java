package org.alexdev.icarus.messages.outgoing.room.notify;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.MessageComposer;

public class FloodFilterMessageComposer extends MessageComposer {

    private int waitSeconds;

    public FloodFilterMessageComposer(int waitSeconds) {
        this.waitSeconds = waitSeconds;
    }

    @Override
    public void write() {
        response.init(Outgoing.FloodFilterMessageComposer);
        response.writeInt(this.waitSeconds);
    }

}
