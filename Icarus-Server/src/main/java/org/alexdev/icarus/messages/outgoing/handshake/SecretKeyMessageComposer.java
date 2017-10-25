package org.alexdev.icarus.messages.outgoing.handshake;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class SecretKeyMessageComposer extends MessageComposer {

    private String publicKey;

    public SecretKeyMessageComposer(String publicKey) {
        this.publicKey = publicKey;
    }

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.SecretKeyMessageComposer);
        response.writeString(this.publicKey);
    }

    @Override
    public short getHeader() {
        return Outgoing.SecretKeyMessageComposer;
    }
}
