package org.alexdev.icarus.messages.outgoing.room.notify;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.MessageComposer;

public class GenericErrorMessageComposer extends MessageComposer {

    private int errorCode;

    public GenericErrorMessageComposer(int errorCode) {
        this.errorCode = errorCode;
    }

    @Override
    public void write() {
        response.init(Outgoing.GenericErrorMessageComposer);
        response.writeInt(this.errorCode);
    }

}
