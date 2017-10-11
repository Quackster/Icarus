package org.alexdev.icarus.messages.outgoing.room.notify;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;

public class GenericNoAnswerDoorbellMessageComposer extends MessageComposer {

    @Override
    public void write() {
        this.response.init(Outgoing.GenericNoAnswerDoorbellMessageComposer);
        this.response.writeString("");
    }
}
