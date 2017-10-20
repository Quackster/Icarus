package org.alexdev.icarus.messages.outgoing.room.notify;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class SettingsUpdatedMessageComposer extends MessageComposer {

    private int roomId;

    public SettingsUpdatedMessageComposer(int roomId) {
        this.roomId = roomId;
    }

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.SettingsUpdatedMessageComposer);
        response.writeInt(this.roomId);
    }

    @Override
    public short getHeader() {
        return Outgoing.SettingsUpdatedMessageComposer;
    }
}