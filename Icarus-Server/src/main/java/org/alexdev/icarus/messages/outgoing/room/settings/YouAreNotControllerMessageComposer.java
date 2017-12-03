package org.alexdev.icarus.messages.outgoing.room.settings;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class YouAreNotControllerMessageComposer extends MessageComposer {

    @Override
    public void compose(Response response) { }

    @Override
    public short getHeader() {
        return Outgoing.YouAreNotControllerMessageComposer;
    }
}