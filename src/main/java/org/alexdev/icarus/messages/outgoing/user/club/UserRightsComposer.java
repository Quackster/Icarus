package org.alexdev.icarus.messages.outgoing.user.club;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class UserRightsComposer extends MessageComposer {

    private boolean hasSubscripton;
    private int rank;

    public UserRightsComposer(boolean hasSubscripton, int rank) {
        this.hasSubscripton = hasSubscripton;
        this.rank = rank;
    }

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.UserRightsComposer);
        response.writeInt(this.hasSubscripton ? 2 : 0);
        response.writeInt(this.rank);
        response.writeBool(false);
    }

    @Override
    public short getHeader() {
        return Outgoing.UserRightsComposer;
    }
}