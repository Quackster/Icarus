package org.alexdev.icarus.messages.outgoing.messenger;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class FollowBuddyMessageComposer extends MessageComposer {

    private int friendId;

    public FollowBuddyMessageComposer(int friendId) {
        this.friendId = friendId;
    }

    @Override
    public void compose(Response response) {

        //response.init(Outgoing.FollowBuddyMessageComposer);
        response.writeInt(this.friendId);
    }

    @Override
    public short getHeader() {
        return Outgoing.FollowBuddyMessageComposer;
    }
}