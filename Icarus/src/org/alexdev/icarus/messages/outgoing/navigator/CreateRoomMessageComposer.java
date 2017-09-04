package org.alexdev.icarus.messages.outgoing.navigator;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.MessageComposer;

public class CreateRoomMessageComposer extends MessageComposer {

    private int id;
    private String name;

    public CreateRoomMessageComposer(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public void write() {
        response.init(Outgoing.CreateRoomMessageComposer);
        response.writeInt(this.id);
        response.writeString(this.name);
    }

}
