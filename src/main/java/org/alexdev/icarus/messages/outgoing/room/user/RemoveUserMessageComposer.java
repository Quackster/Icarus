package org.alexdev.icarus.messages.outgoing.room.user;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class RemoveUserMessageComposer extends MessageComposer {

    private int virtualId;

    public RemoveUserMessageComposer(int virtualId) {
        this.virtualId = virtualId;
    }

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.RemoveUserMessageComposer);
        response.writeString(this.virtualId + "");
    }

    @Override
    public short getHeader() {
        return Outgoing.RemoveUserMessageComposer;
    }
}
