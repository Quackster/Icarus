package org.alexdev.icarus.messages.outgoing.room.settings;

import org.alexdev.icarus.messages.MessageComposer;
import org.alexdev.icarus.messages.headers.Outgoing;

public class RightsAssignedComposer extends MessageComposer {

    private int roomID;
    private int userID;
    private String name;

    public RightsAssignedComposer(int roomID, int userID, String name) {
        this.roomID = roomID;
        this.userID = userID;
        this.name = name;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.RightsAssignedComposer);
        this.response.writeInt(this.roomID);
        this.response.writeInt(this.userID);
        this.response.writeString(this.name);
    }
}
