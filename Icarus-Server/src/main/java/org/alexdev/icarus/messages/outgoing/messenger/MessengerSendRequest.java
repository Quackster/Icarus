package org.alexdev.icarus.messages.outgoing.messenger;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

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
    public void compose(Response response) {
        //response.init(Outgoing.MessengerSendRequest);
        response.writeInt(this.id);
        response.writeString(this.username);
        response.writeString(this.figure);
    }

    @Override
    public short getHeader() {
        return Outgoing.MessengerSendRequest;
    }
}