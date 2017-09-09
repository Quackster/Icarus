package org.alexdev.icarus.messages.outgoing.room.settings;

import org.alexdev.icarus.messages.MessageComposer;
import org.alexdev.icarus.messages.headers.Outgoing;

public class RightsRemovedComposer extends MessageComposer {

    private int roomID;
    private int userID;

    public RightsRemovedComposer(int roomID, int userID) {
        this.roomID = roomID;
        this.userID = userID;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.RightsRemovedComposer);
        this.response.writeInt(this.roomID);
        this.response.writeInt(this.userID);
    }
}
