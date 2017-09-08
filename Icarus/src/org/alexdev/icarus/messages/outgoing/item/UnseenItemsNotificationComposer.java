package org.alexdev.icarus.messages.outgoing.item;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.MessageComposer;

public class UnseenItemsNotificationComposer extends MessageComposer {

    private int itemId;
    private int type;

    public UnseenItemsNotificationComposer(int itemId, int type) {
        this.itemId = itemId;
        this.type = type;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.FurniListNotificationComposer);
        this.response.writeInt(1);
        this.response.writeInt(this.type);
        this.response.writeInt(1);
        this.response.writeInt(this.itemId);
    }
}
