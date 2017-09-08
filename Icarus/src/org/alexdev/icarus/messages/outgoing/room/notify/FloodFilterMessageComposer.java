package org.alexdev.icarus.messages.outgoing.room.notify;

import org.alexdev.icarus.messages.MessageComposer;
import org.alexdev.icarus.messages.headers.Outgoing;

public class FloodFilterMessageComposer extends MessageComposer {

    private int waitSeconds;

    public FloodFilterMessageComposer(int waitSeconds) {
        this.waitSeconds = waitSeconds;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.FloodFilterMessageComposer);
        this.response.writeInt(this.waitSeconds);
    }
}
