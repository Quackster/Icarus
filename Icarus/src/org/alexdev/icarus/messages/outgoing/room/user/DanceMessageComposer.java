package org.alexdev.icarus.messages.outgoing.room.user;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;

public class DanceMessageComposer extends OutgoingMessageComposer {

    private int virtualId;
    private int danceId;

    public DanceMessageComposer(int virtualId, int danceId) {
        this.virtualId = virtualId;
        this.danceId = danceId;
    }

    @Override
    public void write() {
        response.init(Outgoing.DanceStatusMessageComposer);
        response.writeInt(this.virtualId);
        response.writeInt(this.danceId);
    }

}
