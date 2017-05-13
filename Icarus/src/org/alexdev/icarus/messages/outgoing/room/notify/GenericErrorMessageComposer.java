package org.alexdev.icarus.messages.outgoing.room.notify;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;

public class GenericErrorMessageComposer extends OutgoingMessageComposer {

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
