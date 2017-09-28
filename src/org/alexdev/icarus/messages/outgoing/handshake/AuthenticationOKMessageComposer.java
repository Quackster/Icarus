package org.alexdev.icarus.messages.outgoing.handshake;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;

public class AuthenticationOKMessageComposer extends MessageComposer {

    public void write() {
        this.response.init(Outgoing.AuthenticationOKMessageComposer);
    }
}
