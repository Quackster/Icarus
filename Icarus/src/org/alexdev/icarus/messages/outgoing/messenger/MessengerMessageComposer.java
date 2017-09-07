package org.alexdev.icarus.messages.outgoing.messenger;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.MessageComposer;

public class MessengerMessageComposer extends MessageComposer {

    private int userId;
    private String message;

    public MessengerMessageComposer(int userId, String message) {
        this.userId = userId;
        this.message = message;
    }

    @Override
    public void write() {
        response.init(Outgoing.MessengerMessageComposer);
        response.writeInt(this.userId);
        response.writeString(this.message);
        response.writeInt(0);
    }
}
