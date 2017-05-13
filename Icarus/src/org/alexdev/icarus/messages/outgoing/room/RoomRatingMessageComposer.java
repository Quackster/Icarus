package org.alexdev.icarus.messages.outgoing.room;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;

public class RoomRatingMessageComposer extends OutgoingMessageComposer {

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
