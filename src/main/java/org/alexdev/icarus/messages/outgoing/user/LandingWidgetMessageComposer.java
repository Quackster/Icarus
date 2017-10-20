package org.alexdev.icarus.messages.outgoing.user;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class LandingWidgetMessageComposer extends MessageComposer {

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.LandingWidgetMessageComposer);
        response.writeString("");
        response.writeString("");
    }

    @Override
    public short getHeader() {
        return Outgoing.LandingWidgetMessageComposer;
    }
}