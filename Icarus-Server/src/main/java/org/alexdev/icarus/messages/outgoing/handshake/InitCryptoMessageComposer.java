package org.alexdev.icarus.messages.outgoing.handshake;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class InitCryptoMessageComposer extends MessageComposer {

    private String prime;
    private String generator;

    public InitCryptoMessageComposer(String prime, String generator) {
        this.prime = prime;
        this.generator = generator;
    }

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.InitCryptoMessageComposer);
        response.writeString(this.prime);
        response.writeString(this.generator);
    }

    @Override
    public short getHeader() {
        return Outgoing.InitCryptoMessageComposer;
    }
}