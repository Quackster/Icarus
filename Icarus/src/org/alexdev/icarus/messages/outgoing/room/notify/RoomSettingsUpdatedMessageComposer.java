package org.alexdev.icarus.messages.outgoing.room.notify;

import org.alexdev.icarus.messages.MessageComposer;

public class RoomSettingsUpdatedMessageComposer extends MessageComposer {

    private int roomID;

    public RoomSettingsUpdatedMessageComposer(int roomID) {
        this.roomID = roomID;
    }

    @Override
    public void write() {
        this.response.init(-1);//Outgoing.RoomSettingsUpdatedMessageComposer);
        this.response.writeInt(this.roomID);
    }
}
