package org.alexdev.icarus.messages.outgoing.user;

import org.alexdev.icarus.messages.MessageComposer;
import org.alexdev.icarus.messages.headers.Outgoing;

public class LandingWidgetMessageComposer extends MessageComposer {

    @Override
    public void write() {
        this.response.init(Outgoing.LandingWidgetMessageComposer);
        this.response.writeString("");
        this.response.writeString("");
    }
}
