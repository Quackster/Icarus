package org.alexdev.icarus.messages.outgoing.messenger;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;

public class FollowErrorMessageComposer extends MessageComposer {

    private int errorId;

    public FollowErrorMessageComposer(int errorId) {
        this.errorId = errorId;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.FollowErrorMessageComposer);
        this.response.writeInt(this.errorId);

    }
}
