package org.alexdev.icarus.messages.outgoing.handshake;

import org.alexdev.icarus.messages.MessageComposer;
import org.alexdev.icarus.messages.headers.Outgoing;

public class AvailabilityMessageComposer extends MessageComposer {

    @Override
    public void write() {
        this.response.init(Outgoing.AvailabilityMessageComposer);
        this.response.writeBool(true);
        this.response.writeBool(false);
        this.response.writeBool(true);
    }
}
