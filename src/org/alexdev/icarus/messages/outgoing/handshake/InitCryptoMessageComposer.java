package org.alexdev.icarus.messages.outgoing.handshake;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;

public class InitCryptoMessageComposer extends MessageComposer {

    private String prime;
    private String generator;

    public InitCryptoMessageComposer(String prime, String generator) {
        this.prime = prime;
        this.generator = generator;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.InitCryptoMessageComposer);
        this.response.writeString(this.prime);
        this.response.writeString(this.generator);
    }

}
