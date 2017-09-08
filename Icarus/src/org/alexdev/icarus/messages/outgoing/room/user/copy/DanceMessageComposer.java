package org.alexdev.icarus.messages.outgoing.room.user.copy;

import org.alexdev.icarus.messages.MessageComposer;
import org.alexdev.icarus.messages.headers.Outgoing;

public class DanceMessageComposer extends MessageComposer {

    private int virtualId;
    private int danceId;

    public DanceMessageComposer(int virtualId, int danceId) {
        this.virtualId = virtualId;
        this.danceId = danceId;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.DanceStatusMessageComposer);
        this.response.writeInt(this.virtualId);
        this.response.writeInt(this.danceId);
    }

}
