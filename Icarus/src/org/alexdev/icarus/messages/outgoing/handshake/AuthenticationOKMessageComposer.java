package org.alexdev.icarus.messages.outgoing.handshake;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;

public class AuthenticationOKMessageComposer extends OutgoingMessageComposer {

    public void write() {
        response.init(Outgoing.AuthenticationOKMessageComposer);
    }

}
