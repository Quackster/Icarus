package org.alexdev.icarus.messages.outgoing.handshake;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class AuthenticationOKMessageComposer extends MessageComposer {

    public void compose(Response response) {
        //response.init(Outgoing.AuthenticationOKMessageComposer);
    }

    @Override
    public short getHeader() {
        return Outgoing.AuthenticationOKMessageComposer;
    }
}