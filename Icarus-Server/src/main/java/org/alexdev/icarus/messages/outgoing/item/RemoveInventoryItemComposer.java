package org.alexdev.icarus.messages.outgoing.item;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class RemoveInventoryItemComposer extends MessageComposer {

    private int itemId;

    public RemoveInventoryItemComposer(int gameId) {
        this.itemId = gameId;
    }

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.RemoveInventoryItemComposer);
        response.writeInt(this.itemId);
    }

    @Override
    public short getHeader() {
        return Outgoing.RemoveInventoryItemComposer;
    }
}