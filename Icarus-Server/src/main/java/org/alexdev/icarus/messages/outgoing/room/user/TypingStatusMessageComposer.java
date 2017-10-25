package org.alexdev.icarus.messages.outgoing.room.user;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class TypingStatusMessageComposer extends MessageComposer {

    private int virtualId;
    private boolean typeStart;

    public TypingStatusMessageComposer(int virtualId, boolean typeStart) {
        this.virtualId = virtualId;
        this.typeStart = typeStart;
    }

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.TypingStatusMessageComposer);
        response.writeInt(this.virtualId);
        response.writeInt(this.typeStart);
    }

    @Override
    public short getHeader() {
        return Outgoing.TypingStatusMessageComposer;
    }
}