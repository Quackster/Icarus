package org.alexdev.icarus.messages.outgoing.room.user;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class ThumbnailMessageComposer extends MessageComposer {

    private boolean limitReached;

    public ThumbnailMessageComposer() {
        this.limitReached = false;
    }

    public ThumbnailMessageComposer(boolean limitReached) {
        this.limitReached = limitReached;
    }

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.ThumbnailMessageComposer);
        response.writeBool(this.limitReached ? false : true);
        response.writeBool(true);
    }

    @Override
    public short getHeader() {
        return Outgoing.ThumbnailMessageComposer;
    }
}