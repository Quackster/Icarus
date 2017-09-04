package org.alexdev.icarus.messages.outgoing.user;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.MessageComposer;

public class CreditsMessageComposer extends MessageComposer {

    private int credits;

    public CreditsMessageComposer(int credits) {
        this.credits = credits;
    }

    @Override
    public void write() {
        response.init(Outgoing.CreditsMessageComposer);
        response.writeString(this.credits + ".0");
    }

}
