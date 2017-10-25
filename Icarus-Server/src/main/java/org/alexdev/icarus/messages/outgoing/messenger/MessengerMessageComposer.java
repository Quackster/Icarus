package org.alexdev.icarus.messages.outgoing.messenger;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class MessengerMessageComposer extends MessageComposer {

    private int userId;
    private String message;

    public MessengerMessageComposer(int userId, String message) {
        this.userId = userId;
        this.message = message;
    }

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.MessengerMessageComposer);
        response.writeInt(this.userId);
        response.writeString(this.message);
        response.writeInt(0);
    }

    @Override
    public short getHeader() {
        return Outgoing.MessengerMessageComposer;
    }
}
