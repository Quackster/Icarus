package org.alexdev.icarus.messages.outgoing.room;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;

public class RoomOwnerRightsComposer extends OutgoingMessageComposer {

    private int id;
    private boolean isOwner;

    public RoomOwnerRightsComposer(int id, boolean isOwner) {
        this.id = id;
        this.isOwner = isOwner;
    }

    @Override
    public void write() {
        response.init(Outgoing.RoomOwnerRightsComposer);
        response.writeInt(this.id);
        response.writeBool(this.isOwner);
    }

}
