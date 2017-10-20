package org.alexdev.icarus.messages.outgoing.room;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class OwnerRightsMessageComposer extends MessageComposer {

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.OwnerRightsMessageComposer);
    }

    @Override
    public short getHeader() {
        return Outgoing.OwnerRightsMessageComposer;
    }
}