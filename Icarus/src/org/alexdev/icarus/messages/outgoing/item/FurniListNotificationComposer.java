package org.alexdev.icarus.messages.outgoing.item;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.MessageComposer;

public class FurniListNotificationComposer extends MessageComposer {

    private int itemId;
    private int type;

    public FurniListNotificationComposer(int itemId, int type) {
        this.itemId = itemId;
        this.type = type;
    }

    @Override
    public void write() {
        response.init(Outgoing.FurniListNotificationComposer);
        response.writeInt(1);
        response.writeInt(this.type);
        response.writeInt(1);
        response.writeInt(this.itemId);
    }
}
