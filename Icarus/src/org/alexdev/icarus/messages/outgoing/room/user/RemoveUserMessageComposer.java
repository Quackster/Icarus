package org.alexdev.icarus.messages.outgoing.room.user;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.MessageComposer;

public class RemoveUserMessageComposer extends MessageComposer {

    private int virtualId;

    public RemoveUserMessageComposer(int virtualId) {
        this.virtualId = virtualId;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.RemoveUserMessageComposer);
        this.response.writeString(this.virtualId + "");
    }
}
