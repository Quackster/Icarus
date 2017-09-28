package org.alexdev.icarus.messages.outgoing.handshake;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;

public class SecretKeyMessageComposer extends MessageComposer {

    private String publicKey;

    public SecretKeyMessageComposer(String publicKey) {
        this.publicKey = publicKey;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.SecretKeyMessageComposer);
        this.response.writeString(this.publicKey);
    }

}
