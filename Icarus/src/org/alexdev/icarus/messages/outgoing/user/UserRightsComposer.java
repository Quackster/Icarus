package org.alexdev.icarus.messages.outgoing.user;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;

public class UserRightsComposer extends OutgoingMessageComposer {

    private boolean hasSubscripton;
    private int rank;

    public UserRightsComposer(boolean hasSubscripton, int rank) {
        this.hasSubscripton = hasSubscripton;
        this.rank = rank;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.UserRightsComposer);
        this.response.writeInt(this.hasSubscripton ? 2 : 0);
        this.response.writeInt(this.rank);
        this.response.writeBool(false);
    }

}
