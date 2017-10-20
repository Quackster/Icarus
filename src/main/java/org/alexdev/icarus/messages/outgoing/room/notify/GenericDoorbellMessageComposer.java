package org.alexdev.icarus.messages.outgoing.room.notify;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class GenericDoorbellMessageComposer extends MessageComposer {

    private String username;
    private int notifyCode = -1;

    public GenericDoorbellMessageComposer(String username) {
        this.username = username;
    }

    public GenericDoorbellMessageComposer(int notifyCode) {
        this.notifyCode = notifyCode;
    }

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.GenericDoorbellMessageComposer);

        if (this.username != null) {
            response.writeString(this.username);
        } else {
            response.writeInt(this.notifyCode);
        }
    }

    @Override
    public short getHeader() {
        return Outgoing.GenericDoorbellMessageComposer;
    }
}