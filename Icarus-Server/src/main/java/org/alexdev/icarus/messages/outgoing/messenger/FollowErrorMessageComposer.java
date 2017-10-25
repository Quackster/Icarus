package org.alexdev.icarus.messages.outgoing.messenger;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class FollowErrorMessageComposer extends MessageComposer {

    private int errorId;

    public FollowErrorMessageComposer(int errorId) {
        this.errorId = errorId;
    }

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.FollowErrorMessageComposer);
        response.writeInt(this.errorId);

    }

    @Override
    public short getHeader() {
        return Outgoing.FollowErrorMessageComposer;
    }
}