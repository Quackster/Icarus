package org.alexdev.icarus.messages.outgoing.room.notify;

import org.alexdev.icarus.messages.MessageComposer;
import org.alexdev.icarus.messages.headers.Outgoing;

public class GenericNoAnswerDoorbellMessageComposer extends MessageComposer {

    @Override
    public void write() {
        this.response.init(Outgoing.GenericNoAnswerDoorbellMessageComposer);
        this.response.writeString("");
    }
}
