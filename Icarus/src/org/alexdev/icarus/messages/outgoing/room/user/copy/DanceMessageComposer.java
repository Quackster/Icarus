package org.alexdev.icarus.messages.outgoing.room.user.copy;

import org.alexdev.icarus.messages.MessageComposer;
import org.alexdev.icarus.messages.headers.Outgoing;

public class DanceMessageComposer extends MessageComposer {

    private int virtualID;
    private int danceID;

    public DanceMessageComposer(int virtualID, int danceID) {
        this.virtualID = virtualID;
        this.danceID = danceID;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.DanceStatusMessageComposer);
        this.response.writeInt(this.virtualID);
        this.response.writeInt(this.danceID);
    }

}
