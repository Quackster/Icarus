package org.alexdev.icarus.messages.outgoing.handshake;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class GenerateSecretKeyComposer extends MessageComposer {

    public String publicKey;

    public GenerateSecretKeyComposer(String encryptedPublicKey) {
        this.publicKey = encryptedPublicKey;
    }

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.SecretKeyMessageComposer);    
        response.writeString(this.publicKey);
        response.writeBool(false);
    }

    @Override
    public short getHeader() {
        return Outgoing.SecretKeyMessageComposer;
    }
}