package org.alexdev.icarus.messages.outgoing.room.settings;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class RightsRemovedComposer extends MessageComposer {

    private int roomId;
    private int userId;

    public RightsRemovedComposer(int roomId, int userId) {
        this.roomId = roomId;
        this.userId = userId;
    }

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.RightsRemovedComposer);
        response.writeInt(this.roomId);
        response.writeInt(this.userId);
    }

    @Override
    public short getHeader() {
        return Outgoing.RightsRemovedComposer;
    }
}