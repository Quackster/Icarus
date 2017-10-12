package org.alexdev.icarus.messages.outgoing.room.user;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;

public class ThumbnailMessageComposer extends MessageComposer {

    private boolean limitReached;

    public ThumbnailMessageComposer() {
        this.limitReached = false;
    }
    
    public ThumbnailMessageComposer(boolean limitReached) {
        this.limitReached = limitReached;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.ThumbnailMessageComposer);
        this.response.writeBool(this.limitReached ? false : true);
        this.response.writeBool(true);
    }

}
