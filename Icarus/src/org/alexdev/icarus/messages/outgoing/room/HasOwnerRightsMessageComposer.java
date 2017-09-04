package org.alexdev.icarus.messages.outgoing.room;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.MessageComposer;

public class HasOwnerRightsMessageComposer extends MessageComposer {

    @Override
    public void write() {
        response.init(Outgoing.HasOwnerRightsMessageComposer);
    }
}
