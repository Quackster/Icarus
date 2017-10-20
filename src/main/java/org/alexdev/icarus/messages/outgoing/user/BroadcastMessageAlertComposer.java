package org.alexdev.icarus.messages.outgoing.user;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class BroadcastMessageAlertComposer extends MessageComposer {

    private String message;

    public BroadcastMessageAlertComposer(String message) {
        this.message = message;
    }

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.BroadcastMessageAlertComposer);
        response.writeString(this.message);
        response.writeString(""); // TODO: URL
    }

    @Override
    public short getHeader() {
        return Outgoing.BroadcastMessageAlertComposer;
    }
}
