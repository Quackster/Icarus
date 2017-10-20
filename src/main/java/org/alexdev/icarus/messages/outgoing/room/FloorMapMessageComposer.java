package org.alexdev.icarus.messages.outgoing.room;

import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class FloorMapMessageComposer extends MessageComposer {

    private Room room;

    public FloorMapMessageComposer(Room room) {
        this.room = room;
    }

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.FloorMapMessageComposer);
        response.writeBool(true);
        response.writeInt(this.room.getData().getWallHeight());
        response.writeString(this.room.getModel().getRelativeHeightmap());
    }

    @Override
    public short getHeader() {
        return Outgoing.FloorMapMessageComposer;
    }
}