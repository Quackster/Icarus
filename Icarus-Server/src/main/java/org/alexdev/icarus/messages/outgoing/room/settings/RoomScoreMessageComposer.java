package org.alexdev.icarus.messages.outgoing.room.settings;

import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class RoomScoreMessageComposer extends MessageComposer {
    private final Room room;

    public RoomScoreMessageComposer(Room room) {
        this.room = room;
    }

    @Override
    public void compose(Response response) {
        response.writeInt(0);
        response.writeBool(true); // can vote?
    }

    @Override
    public short getHeader() {
        return 482;
    }
}
