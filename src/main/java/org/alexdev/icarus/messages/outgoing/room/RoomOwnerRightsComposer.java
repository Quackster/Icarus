package org.alexdev.icarus.messages.outgoing.room;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class RoomOwnerRightsComposer extends MessageComposer {

    private int id;
    private boolean isOwner;

    public RoomOwnerRightsComposer(int id, boolean isOwner) {
        this.id = id;
        this.isOwner = isOwner;
    }

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.RoomOwnerRightsComposer);
        response.writeInt(this.id);
        response.writeBool(this.isOwner);
    }

    @Override
    public short getHeader() {
        return Outgoing.RoomOwnerRightsComposer;
    }
}
