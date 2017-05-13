package org.alexdev.icarus.messages.outgoing.messenger;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;

public class RemoveFriendMessageComposer extends OutgoingMessageComposer {

    private int friendId;

    public RemoveFriendMessageComposer(int friendId) {
        this.friendId = friendId;
    }

    @Override
    public void write() {

        response.init(Outgoing.RemoveFriendMessageComposer);
        response.writeInt(0);
        response.writeInt(1);
        response.writeInt(-1);
        response.writeInt(this.friendId);
    }

}
