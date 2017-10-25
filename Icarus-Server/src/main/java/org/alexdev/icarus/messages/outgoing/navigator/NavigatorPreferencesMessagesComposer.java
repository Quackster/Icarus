package org.alexdev.icarus.messages.outgoing.navigator;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class NavigatorPreferencesMessagesComposer extends MessageComposer {

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.NavigatorPreferencesMessagesComposer);
        response.writeInt(50);//pref.NewnaviX);
        response.writeInt(50);//pref.NewnaviY);
        response.writeInt(580);//pref.NewnaviWidth);
        response.writeInt(600);//pref.NewnaviHeight);
        response.writeInt(true);
        response.writeInt(1);

    }

    @Override
    public short getHeader() {
        return Outgoing.NavigatorPreferencesComposer;
    }
}