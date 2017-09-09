package org.alexdev.icarus.messages.outgoing.messenger;

import org.alexdev.icarus.messages.MessageComposer;
import org.alexdev.icarus.messages.headers.Outgoing;

public class RemoveFriendMessageComposer extends MessageComposer {

    private int friendID;

    public RemoveFriendMessageComposer(int friendID) {
        this.friendID = friendID;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.RemoveFriendMessageComposer);
        this.response.writeInt(0);
        this.response.writeInt(1);
        this.response.writeInt(-1);
        this.response.writeInt(this.friendID);
    }
}
