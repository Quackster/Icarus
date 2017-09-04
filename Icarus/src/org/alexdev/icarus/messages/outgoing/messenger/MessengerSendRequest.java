package org.alexdev.icarus.messages.outgoing.messenger;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.MessageComposer;

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
        response.init(Outgoing.MessengerSendRequest);
        response.writeInt(this.id);
        response.writeString(this.username);
        response.writeString(this.figure);
    }

}
