package org.alexdev.icarus.messages.outgoing.handshake;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class AvailabilityMessageComposer extends MessageComposer {

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.AvailabilityMessageComposer);
        response.writeBool(true);
        response.writeBool(true);
        response.writeBool(true);
    }

    @Override
    public short getHeader() {
        return Outgoing.AvailabilityMessageComposer;
    }
}