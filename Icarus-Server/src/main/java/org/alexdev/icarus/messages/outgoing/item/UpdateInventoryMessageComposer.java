package org.alexdev.icarus.messages.outgoing.item;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class UpdateInventoryMessageComposer extends MessageComposer {

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.UpdateInventoryMessageComposer);

    }

    @Override
    public short getHeader() {
        return Outgoing.UpdateInventoryMessageComposer;
    }
}