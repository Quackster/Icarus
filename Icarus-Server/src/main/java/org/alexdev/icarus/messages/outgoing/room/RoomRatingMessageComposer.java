package org.alexdev.icarus.messages.outgoing.room;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class RoomRatingMessageComposer extends MessageComposer {

    private int score;

    public RoomRatingMessageComposer(int score) {
        this.score = score;
    }

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.RoomRatingMessageComposer);
        response.writeInt(this.score);
        response.writeBool(false);
    }

    @Override
    public short getHeader() {
        return Outgoing.RoomRatingMessageComposer;
    }
}
