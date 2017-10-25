package org.alexdev.icarus.messages.outgoing.user;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class MOTDNotificationMessageComposer extends MessageComposer {

    private String message;

    public MOTDNotificationMessageComposer(String message) {
        this.message = message;
    }

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.MOTDNotificationMessageComposer);
        response.writeInt(1);
        response.writeString(this.message);
    }

    @Override
    public short getHeader() {
        return Outgoing.MOTDNotificationMessageComposer;
    }
}
