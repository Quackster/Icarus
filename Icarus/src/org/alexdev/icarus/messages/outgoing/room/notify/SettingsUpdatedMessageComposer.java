package org.alexdev.icarus.messages.outgoing.room.notify;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;

public class SettingsUpdatedMessageComposer extends MessageComposer {

    private int roomId;

    public SettingsUpdatedMessageComposer(int roomId) {
        this.roomId = roomId;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.SettingsUpdatedMessageComposer);
        this.response.writeInt(this.roomId);
    }
}
