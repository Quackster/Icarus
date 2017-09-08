package org.alexdev.icarus.messages.outgoing.room.user.copy;

import org.alexdev.icarus.messages.MessageComposer;
import org.alexdev.icarus.messages.headers.Outgoing;

public class TypingStatusMessageComposer extends MessageComposer {

    private int virtualId;
    private boolean typeStart;

    public TypingStatusMessageComposer(int virtualId, boolean typeStart) {
        this.virtualId = virtualId;
        this.typeStart = typeStart;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.TypingStatusMessageComposer);
        this.response.writeInt(this.virtualId);
        this.response.writeInt(this.typeStart);
    }
}
