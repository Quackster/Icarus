package org.alexdev.icarus.messages.outgoing.messenger;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;

public class MessengerMessageComposer extends MessageComposer {

    private int userId;
    private String message;

    public MessengerMessageComposer(int userId, String message) {
        this.userId = userId;
        this.message = message;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.MessengerMessageComposer);
        this.response.writeInt(this.userId);
        this.response.writeString(this.message);
        this.response.writeInt(0);
    }
}
