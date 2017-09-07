package org.alexdev.icarus.messages.outgoing.user;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.MessageComposer;

public class LandingWidgetMessageComposer extends MessageComposer {

    @Override
    public void write() {
        this.response.init(Outgoing.LandingWidgetMessageComposer);
        this.response.writeString("");
        this.response.writeString("");
    }
}
