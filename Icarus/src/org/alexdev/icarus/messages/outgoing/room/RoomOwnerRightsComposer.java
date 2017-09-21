package org.alexdev.icarus.messages.outgoing.room;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;

public class RoomOwnerRightsComposer extends MessageComposer {

    private int id;
    private boolean isOwner;

    public RoomOwnerRightsComposer(int id, boolean isOwner) {
        this.id = id;
        this.isOwner = isOwner;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.RoomOwnerRightsComposer);
        this.response.writeInt(this.id);
        this.response.writeBool(this.isOwner);
    }
}
