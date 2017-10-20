package org.alexdev.icarus.messages.outgoing.groups.members;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class UnknownGroupComposer extends MessageComposer {

    private int groupId;
    private int userId;

    public UnknownGroupComposer(int groupId, int userId) {
        this.groupId = groupId;
        this.userId = userId;
    }

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.UnknownGroupComposer);
        response.writeInt(this.groupId);
        response.writeInt(this.userId);

    }

    @Override
    public short getHeader() {
        return Outgoing.UnknownGroupComposer;
    }
}