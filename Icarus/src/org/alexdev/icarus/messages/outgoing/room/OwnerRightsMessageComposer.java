package org.alexdev.icarus.messages.outgoing.room;

import org.alexdev.icarus.messages.MessageComposer;
import org.alexdev.icarus.messages.headers.Outgoing;

public class OwnerRightsMessageComposer extends MessageComposer {

    @Override
    public void write() {
        this.response.init(Outgoing.OwnerRightsMessageComposer);
    }
}
