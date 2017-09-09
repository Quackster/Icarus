package org.alexdev.icarus.messages.outgoing.item;

import org.alexdev.icarus.messages.MessageComposer;
import org.alexdev.icarus.messages.headers.Outgoing;

public class RemoveInventoryItemComposer extends MessageComposer {

    private int itemID;

    public RemoveInventoryItemComposer(int gameID) {
        this.itemID = gameID;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.RemoveInventoryItemComposer);
        this.response.writeInt(this.itemID);
    }
}
