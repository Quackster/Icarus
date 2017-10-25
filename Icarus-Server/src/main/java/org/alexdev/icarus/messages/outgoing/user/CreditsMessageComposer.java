package org.alexdev.icarus.messages.outgoing.user;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class CreditsMessageComposer extends MessageComposer {

    private int credits;

    public CreditsMessageComposer(int credits) {
        this.credits = credits;
    }

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.CreditsMessageComposer);
        response.writeString(this.credits + ".0");
    }

    @Override
    public short getHeader() {
        return Outgoing.CreditsMessageComposer;
    }
}