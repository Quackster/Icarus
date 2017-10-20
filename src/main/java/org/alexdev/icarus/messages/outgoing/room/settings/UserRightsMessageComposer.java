package org.alexdev.icarus.messages.outgoing.room.settings;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class UserRightsMessageComposer extends MessageComposer {

    private boolean hasSubscripton;
    private int rank;

    public UserRightsMessageComposer(boolean hasSubscripton, int rank) {
        this.hasSubscripton = hasSubscripton;
        this.rank = rank;
    }

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.UserRightsMessageComposer);
        response.writeInt(this.hasSubscripton ? 2 : 0);
        response.writeInt(this.rank);
        response.writeBool(false);
    }

    @Override
    public short getHeader() {
        return Outgoing.UserRightsMessageComposer;
    }
}