package org.alexdev.icarus.messages.outgoing.room.notify;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;

public class GenericNoAnswerDoorbellMessageComposer extends OutgoingMessageComposer {

    @Override
    public void write() {
        response.init(Outgoing.GenericNoAnswerDoorbellMessageComposer);
        response.writeString("");
    }
}
