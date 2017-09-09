package org.alexdev.icarus.messages.outgoing.messenger;

import org.alexdev.icarus.messages.MessageComposer;
import org.alexdev.icarus.messages.headers.Outgoing;

public class MessengerMessageComposer extends MessageComposer {

    private int userID;
    private String message;

    public MessengerMessageComposer(int userID, String message) {
        this.userID = userID;
        this.message = message;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.MessengerMessageComposer);
        this.response.writeInt(this.userID);
        this.response.writeString(this.message);
        this.response.writeInt(0);
    }
}
