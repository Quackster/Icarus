package org.alexdev.icarus.messages.outgoing.room.user.copy;

import org.alexdev.icarus.messages.MessageComposer;
import org.alexdev.icarus.messages.headers.Outgoing;

public class RemoveUserMessageComposer extends MessageComposer {

    private int virtualID;

    public RemoveUserMessageComposer(int virtualID) {
        this.virtualID = virtualID;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.RemoveUserMessageComposer);
        this.response.writeString(this.virtualID + "");
    }
}
