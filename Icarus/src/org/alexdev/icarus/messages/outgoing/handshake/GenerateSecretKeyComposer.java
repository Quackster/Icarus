package org.alexdev.icarus.messages.outgoing.handshake;

import org.alexdev.icarus.messages.MessageComposer;
import org.alexdev.icarus.messages.headers.Outgoing;

public class GenerateSecretKeyComposer extends MessageComposer {
    
    public String publicKey;
        
    public GenerateSecretKeyComposer(String encryptedPublicKey) {
        this.publicKey = encryptedPublicKey;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.SecretKeyMessageComposer);    
        this.response.writeString(this.publicKey);
        this.response.writeBool(false);
    }
}
