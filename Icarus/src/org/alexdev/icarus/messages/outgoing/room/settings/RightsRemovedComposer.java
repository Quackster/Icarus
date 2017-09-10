package org.alexdev.icarus.messages.outgoing.room.settings;

import org.alexdev.icarus.messages.MessageComposer;
import org.alexdev.icarus.messages.headers.Outgoing;

public class RightsRemovedComposer extends MessageComposer {

    private int roomId;
    private int userId;

    public RightsRemovedComposer(int roomId, int userId) {
        this.roomId = roomId;
        this.userId = userId;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.RightsRemovedComposer);
        this.response.writeInt(this.roomId);
        this.response.writeInt(this.userId);
    }
}
