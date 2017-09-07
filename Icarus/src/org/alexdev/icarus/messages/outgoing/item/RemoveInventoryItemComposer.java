package org.alexdev.icarus.messages.outgoing.item;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.MessageComposer;

public class RemoveInventoryItemComposer extends MessageComposer {

    private int itemId;

    public RemoveInventoryItemComposer(int gameId) {
        this.itemId = gameId;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.RemoveInventoryItemComposer);
        this.response.writeInt(this.itemId);
    }
}
