package org.alexdev.icarus.messages.outgoing.room.settings;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

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
    public void compose(Response response) {
        //response.init(Outgoing.RightsAssignedComposer);
        response.writeInt(this.roomId);
        response.writeInt(this.userId);
        response.writeString(this.name);
    }

    @Override
    public short getHeader() {
        return Outgoing.RightsAssignedComposer;
    }
}