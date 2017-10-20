package org.alexdev.icarus.messages.outgoing.messenger;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class RemoveFriendMessageComposer extends MessageComposer {

    private int friendId;

    public RemoveFriendMessageComposer(int friendId) {
        this.friendId = friendId;
    }

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.RemoveFriendMessageComposer);
        response.writeInt(0);
        response.writeInt(1);
        response.writeInt(-1);
        response.writeInt(this.friendId);
    }

    @Override
    public short getHeader() {
        return Outgoing.RemoveFriendMessageComposer;
    }
}
