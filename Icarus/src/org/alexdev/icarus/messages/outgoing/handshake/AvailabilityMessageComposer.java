package org.alexdev.icarus.messages.outgoing.handshake;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.MessageComposer;

public class AvailabilityMessageComposer extends MessageComposer {

    @Override
    public void write() {
        response.init(Outgoing.AvailabilityMessageComposer);
        response.writeBool(true);
        response.writeBool(false);
        response.writeBool(true);
    }

}
