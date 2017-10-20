package org.alexdev.icarus.messages.outgoing.room.notify;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class FloodFilterMessageComposer extends MessageComposer {

    private int waitSeconds;

    public FloodFilterMessageComposer(int waitSeconds) {
        this.waitSeconds = waitSeconds;
    }

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.FloodFilterMessageComposer);
        response.writeInt(this.waitSeconds);
    }

    @Override
    public short getHeader() {
        return Outgoing.FloodFilterMessageComposer;
    }
}