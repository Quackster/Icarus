package org.alexdev.icarus.messages.outgoing.room.user;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;

public class RemoveUserMessageComposer extends OutgoingMessageComposer {

    private int virtualId;

    public RemoveUserMessageComposer(int virtualId) {
        this.virtualId = virtualId;
    }

    @Override
    public void write() {
        response.init(Outgoing.RemoveUserMessageComposer);
        response.writeString(this.virtualId + "");
    }

}
