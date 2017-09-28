package org.alexdev.icarus.messages.outgoing.messenger;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;

public class MessengerSendRequest extends MessageComposer {

    private int id;
    private String username;
    private String figure;

    public MessengerSendRequest(int id, String username, String figure) {
        this.id = id;
        this.username = username;
        this.figure = figure;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.MessengerSendRequest);
        this.response.writeInt(this.id);
        this.response.writeString(this.username);
        this.response.writeString(this.figure);
    }
}
