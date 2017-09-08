package org.alexdev.icarus.messages.outgoing.room;

import org.alexdev.icarus.messages.MessageComposer;
import org.alexdev.icarus.messages.headers.Outgoing;

public class RoomRightsLevelMessageComposer extends MessageComposer {

    private int status;

    public RoomRightsLevelMessageComposer(int status) {
        this.status = status;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.RightsLevelMessageComposer);
        this.response.writeInt(this.status);
    }
}
