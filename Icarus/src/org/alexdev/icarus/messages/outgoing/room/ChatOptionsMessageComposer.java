package org.alexdev.icarus.messages.outgoing.room;

import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.MessageComposer;

public class ChatOptionsMessageComposer extends MessageComposer {

    private Room room;

    public ChatOptionsMessageComposer(Room room) {
        this.room = room;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.ChatOptionsMessageComposer);
        this.response.writeInt(room.getData().getChatMode());
        this.response.writeInt(room.getData().getChatSize());
        this.response.writeInt(room.getData().getChatSpeed());
        this.response.writeInt(room.getData().getChatMaxDistance());
        this.response.writeInt(room.getData().getChatFloodProtection());
    }
}
