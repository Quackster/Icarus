package org.alexdev.icarus.messages.outgoing.room;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.MessageComposer;

public class RoomRatingMessageComposer extends MessageComposer {

    private int score;

    public RoomRatingMessageComposer(int score) {
        this.score = score;
    }

    @Override
    public void write() {
        response.init(Outgoing.RoomRatingMessageComposer);
        response.writeInt(this.score);
        response.writeBool(false);
    }
}
