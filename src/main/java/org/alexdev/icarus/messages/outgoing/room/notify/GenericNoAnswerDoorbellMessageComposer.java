package org.alexdev.icarus.messages.outgoing.room.notify;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class GenericNoAnswerDoorbellMessageComposer extends MessageComposer {

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.GenericNoAnswerDoorbellMessageComposer);
        response.writeString("");
    }

    @Override
    public short getHeader() {
        return Outgoing.GenericNoAnswerDoorbellMessageComposer;
    }
}