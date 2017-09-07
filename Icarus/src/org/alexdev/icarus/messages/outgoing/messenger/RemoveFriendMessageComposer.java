package org.alexdev.icarus.messages.outgoing.messenger;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.MessageComposer;

public class RemoveFriendMessageComposer extends MessageComposer {

    private int friendId;

    public RemoveFriendMessageComposer(int friendId) {
        this.friendId = friendId;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.RemoveFriendMessageComposer);
        this.response.writeInt(0);
        this.response.writeInt(1);
        this.response.writeInt(-1);
        this.response.writeInt(this.friendId);
    }
}
