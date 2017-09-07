package org.alexdev.icarus.messages.outgoing.room.notify;

import org.alexdev.icarus.messages.parsers.MessageComposer;

public class RoomSettingsUpdatedMessageComposer extends MessageComposer {

    private int roomId;

    public RoomSettingsUpdatedMessageComposer(int roomId) {
        this.roomId = roomId;
    }

    @Override
    public void write() {
        response.init(-1);//Outgoing.RoomSettingsUpdatedMessageComposer);
        response.writeInt(this.roomId);
    }
}
