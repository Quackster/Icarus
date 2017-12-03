package org.alexdev.icarus.messages.outgoing.room.settings;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class YouAreControllerMessageComposer extends MessageComposer {

    private int status;

    public YouAreControllerMessageComposer(int status) {
        this.status = status;
    }

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.YouAreControllerMessageComposer);
        response.writeInt(this.status);
    }

    @Override
    public short getHeader() {
        return Outgoing.RightsLevelMessageComposer;
    }
}