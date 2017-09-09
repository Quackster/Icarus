package org.alexdev.icarus.messages.outgoing.room.user.copy;

import org.alexdev.icarus.messages.MessageComposer;
import org.alexdev.icarus.messages.headers.Outgoing;

public class TypingStatusMessageComposer extends MessageComposer {

    private int virtualID;
    private boolean typeStart;

    public TypingStatusMessageComposer(int virtualID, boolean typeStart) {
        this.virtualID = virtualID;
        this.typeStart = typeStart;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.TypingStatusMessageComposer);
        this.response.writeInt(this.virtualID);
        this.response.writeInt(this.typeStart);
    }
}
