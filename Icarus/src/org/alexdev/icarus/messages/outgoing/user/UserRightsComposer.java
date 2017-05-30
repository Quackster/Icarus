package org.alexdev.icarus.messages.outgoing.user;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;

public class UserRightsComposer extends OutgoingMessageComposer {

    private boolean isExpired;
    private int rank;

    public UserRightsComposer(boolean isExpired, int rank) {
        this.isExpired = isExpired;
        this.rank = rank;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.UserRightsComposer);
        this.response.writeInt(this.isExpired ? 0 : 1);
        this.response.writeInt(this.rank);
        this.response.writeBool(false);
    }

}
