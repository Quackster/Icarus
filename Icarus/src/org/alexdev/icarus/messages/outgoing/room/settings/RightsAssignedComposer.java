package org.alexdev.icarus.messages.outgoing.room.settings;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;

public class RightsAssignedComposer extends MessageComposer {

    private int roomId;
    private int userId;
    private String name;

    public RightsAssignedComposer(int roomId, int userId, String name) {
        this.roomId = roomId;
        this.userId = userId;
        this.name = name;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.RightsAssignedComposer);
        this.response.writeInt(this.roomId);
        this.response.writeInt(this.userId);
        this.response.writeString(this.name);
    }
}
