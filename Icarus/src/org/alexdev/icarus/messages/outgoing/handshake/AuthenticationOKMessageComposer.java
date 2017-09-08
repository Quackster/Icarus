package org.alexdev.icarus.messages.outgoing.handshake;

import org.alexdev.icarus.messages.MessageComposer;
import org.alexdev.icarus.messages.headers.Outgoing;

public class AuthenticationOKMessageComposer extends MessageComposer {

    public void write() {
        this.response.init(Outgoing.AuthenticationOKMessageComposer);
    }
}
