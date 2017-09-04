package org.alexdev.icarus.messages.outgoing.room;

import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.MessageComposer;

public class PrepareRoomMessageComposer extends MessageComposer {

    private Room room;

    public PrepareRoomMessageComposer(Room room) {
        this.room = room;
    }

    @Override
    public void write() {
        response.init(Outgoing.RoomUpdateMessageComposer);
        response.writeInt(room.getData().getId());
    }
}
