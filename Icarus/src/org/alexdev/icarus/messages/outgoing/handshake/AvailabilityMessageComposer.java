package org.alexdev.icarus.messages.outgoing.handshake;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class AvailabilityMessageComposer extends OutgoingMessageComposer {

    @Override
    public void write() {
        response.init(Outgoing.AvailabilityMessageComposer);
        response.writeBool(true);
        response.writeBool(false);
        response.writeBool(true);
    }

}
