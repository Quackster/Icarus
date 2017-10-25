package org.alexdev.icarus.messages.outgoing.groups;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class NewGroupInfoMessageComposer extends MessageComposer {

    private int roomId;
    private int groupId;

    public NewGroupInfoMessageComposer(int roomId, int groupId) {
        this.roomId = roomId;
        this.groupId = groupId;
    }

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.NewGroupInfoMessageComposer);
        response.writeInt(this.roomId);
        response.writeInt(this.groupId);
    }

    @Override
    public short getHeader() {
        return Outgoing.NewGroupInfoMessageComposer;
    }
}