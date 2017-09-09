package org.alexdev.icarus.messages.outgoing.messenger;

import org.alexdev.icarus.messages.MessageComposer;
import org.alexdev.icarus.messages.headers.Outgoing;

public class FollowBuddyMessageComposer extends MessageComposer {

    private int friendID;

    public FollowBuddyMessageComposer(int friendID) {
        this.friendID = friendID;
    }

    @Override
    public void write() {
        
        this.response.init(Outgoing.FollowBuddyMessageComposer);
        this.response.writeInt(this.friendID);
    }
}
