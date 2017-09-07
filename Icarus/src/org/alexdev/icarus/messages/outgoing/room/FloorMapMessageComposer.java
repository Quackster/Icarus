package org.alexdev.icarus.messages.outgoing.room;

import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.MessageComposer;

public class FloorMapMessageComposer extends MessageComposer {

    private Room room;

    public FloorMapMessageComposer(Room room) {
        this.room = room;
    }

    @Override
    public void write() {
        response.init(Outgoing.FloorMapMessageComposer);
        response.writeBool(true);
        response.writeInt(this.room.getData().getWallHeight());
        response.writeString(this.room.getModel().getRelativeHeightmap());
    }
}
