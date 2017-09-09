package org.alexdev.icarus.messages.outgoing.item;

import org.alexdev.icarus.messages.MessageComposer;
import org.alexdev.icarus.messages.headers.Outgoing;

public class UnseenItemsNotificationComposer extends MessageComposer {

    private int itemID;
    private int type;

    public UnseenItemsNotificationComposer(int itemID, int type) {
        this.itemID = itemID;
        this.type = type;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.FurniListNotificationComposer);
        this.response.writeInt(1);
        this.response.writeInt(this.type);
        this.response.writeInt(1);
        this.response.writeInt(this.itemID);
    }
}
