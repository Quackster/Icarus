package org.alexdev.icarus.messages.outgoing.room;

import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.messages.MessageComposer;
import org.alexdev.icarus.messages.headers.Outgoing;

public class FloorMapMessageComposer extends MessageComposer {

    private Room room;

    public FloorMapMessageComposer(Room room) {
        this.room = room;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.FloorMapMessageComposer);
        this.response.writeBool(true);
        this.response.writeInt(this.room.getData().getWallHeight());
        this.response.writeString(this.room.getModel().getRelativeHeightmap());
    }
}
