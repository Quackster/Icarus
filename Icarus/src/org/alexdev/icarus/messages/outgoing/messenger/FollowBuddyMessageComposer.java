package org.alexdev.icarus.messages.outgoing.messenger;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.MessageComposer;

public class FollowBuddyMessageComposer extends MessageComposer {

    private int friendId;

    public FollowBuddyMessageComposer(int friendId) {
        this.friendId = friendId;
    }

    @Override
    public void write() {
        
        this.response.init(Outgoing.FollowBuddyMessageComposer);
        this.response.writeInt(this.friendId);
    }
}
