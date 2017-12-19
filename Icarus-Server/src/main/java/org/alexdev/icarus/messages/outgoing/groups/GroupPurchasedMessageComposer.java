package org.alexdev.icarus.messages.outgoing.groups;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class GroupPurchasedMessageComposer extends MessageComposer {

    private int roomId;
    private int groupId;

    public GroupPurchasedMessageComposer(int roomId, int groupId) {
        this.roomId = roomId;
        this.groupId = groupId;
    }

    @Override
    public void compose(Response response) {
        response.writeInt(this.roomId);
        response.writeInt(this.groupId);
    }

    @Override
    public short getHeader() {
        return Outgoing.GroupPurchasedMessageComposer;
    }
}