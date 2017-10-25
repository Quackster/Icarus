package org.alexdev.icarus.messages.outgoing.room.notify;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class GenericErrorMessageComposer extends MessageComposer {

    private int errorCode;

    public GenericErrorMessageComposer(int errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.GenericErrorMessageComposer);
        response.writeInt(this.errorCode);
    }

    @Override
    public short getHeader() {
        return Outgoing.GenericErrorMessageComposer;
    }
}