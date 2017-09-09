package org.alexdev.icarus.messages.outgoing.room.notify;

import org.alexdev.icarus.messages.MessageComposer;
import org.alexdev.icarus.messages.headers.Outgoing;

public class SettingsUpdatedMessageComposer extends MessageComposer {

    private int roomID;

    public SettingsUpdatedMessageComposer(int roomID) {
        this.roomID = roomID;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.SettingsUpdatedMessageComposer);
        this.response.writeInt(this.roomID);
    }
}
