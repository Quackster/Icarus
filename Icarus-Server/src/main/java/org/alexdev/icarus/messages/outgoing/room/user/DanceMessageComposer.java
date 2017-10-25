package org.alexdev.icarus.messages.outgoing.room.user;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;
import org.alexdev.icarus.server.api.messages.Response;

public class DanceMessageComposer extends MessageComposer {

    private int virtualId;
    private int danceId;

    public DanceMessageComposer(int virtualId, int danceId) {
        this.virtualId = virtualId;
        this.danceId = danceId;
    }

    @Override
    public void compose(Response response) {
        response.writeInt(this.virtualId);
        response.writeInt(this.danceId);
    }

    @Override
    public short getHeader() {
        return Outgoing.DanceStatusMessageComposer;
    }
}
