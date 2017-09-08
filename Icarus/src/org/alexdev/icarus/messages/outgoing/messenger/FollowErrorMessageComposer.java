package org.alexdev.icarus.messages.outgoing.messenger;

import org.alexdev.icarus.messages.MessageComposer;
import org.alexdev.icarus.messages.headers.Outgoing;

public class FollowErrorMessageComposer extends MessageComposer {

    private int errorID;

    public FollowErrorMessageComposer(int errorID) {
        this.errorID = errorID;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.FollowErrorMessageComposer);
        this.response.writeInt(this.errorID);

    }
}
