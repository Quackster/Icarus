package org.alexdev.icarus.messages.outgoing.room;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class RightsLevelMessageComposer extends MessageComposer {

    private int status;

    public RightsLevelMessageComposer(int status) {
        this.status = status;
    }

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.RightsLevelMessageComposer);
        response.writeInt(this.status);
    }

    @Override
    public short getHeader() {
        return Outgoing.RightsLevelMessageComposer;
    }
}