package org.alexdev.icarus.messages.outgoing.item;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class UnseenItemsNotificationComposer extends MessageComposer {

    private int itemId;
    private int type;

    public UnseenItemsNotificationComposer(int itemId, int type) {
        this.itemId = itemId;
        this.type = type;
    }

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.FurniListNotificationComposer);
        response.writeInt(1);
        response.writeInt(this.type);
        response.writeInt(1);
        response.writeInt(this.itemId);
    }

    @Override
    public short getHeader() {
        return Outgoing.FurniListNotificationComposer;
    }
}