package org.alexdev.icarus.messages.outgoing.handshake;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;

public class AvailabilityMessageComposer extends MessageComposer {

    @Override
    public void write() {
        this.response.init(Outgoing.AvailabilityMessageComposer);
        this.response.writeBool(true);
        this.response.writeBool(false);
        this.response.writeBool(true);
    }
}
