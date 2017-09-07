package org.alexdev.icarus.messages.outgoing.room.notify;

import org.alexdev.icarus.messages.parsers.MessageComposer;

public class RoomSettingsUpdatedMessageComposer extends MessageComposer {

    private int roomId;

    public RoomSettingsUpdatedMessageComposer(int roomId) {
        this.roomId = roomId;
    }

    @Override
    public void write() {
        this.response.init(-1);//Outgoing.RoomSettingsUpdatedMessageComposer);
        this.response.writeInt(this.roomId);
    }
}
