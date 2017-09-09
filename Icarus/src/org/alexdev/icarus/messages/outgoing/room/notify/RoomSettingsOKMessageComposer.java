package org.alexdev.icarus.messages.outgoing.room.notify;

import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.messages.MessageComposer;

public class RoomSettingsOKMessageComposer extends MessageComposer {

    private Room room;

    public RoomSettingsOKMessageComposer(Room room) {
        this.room = room;
    }

    @Override
    public void write() {
        this.response.init(-1);////Outgoing.RoomSettingsOKMessageComposer);
        this.response.writeInt(this.room.getData().getID());
    }
}
