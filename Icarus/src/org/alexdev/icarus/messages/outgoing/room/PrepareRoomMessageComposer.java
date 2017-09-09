package org.alexdev.icarus.messages.outgoing.room;

import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.messages.MessageComposer;
import org.alexdev.icarus.messages.headers.Outgoing;

public class PrepareRoomMessageComposer extends MessageComposer {

    private Room room;

    public PrepareRoomMessageComposer(Room room) {
        this.room = room;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.RoomUpdateMessageComposer);
        this.response.writeInt(room.getData().getID());
    }
}
