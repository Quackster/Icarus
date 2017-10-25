package org.alexdev.icarus.messages.outgoing.navigator;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class CreateRoomMessageComposer extends MessageComposer {

    private int id;
    private String name;

    public CreateRoomMessageComposer(int id, String name) {
        this.id = id;
        this.name = name;
    }

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.CreateRoomMessageComposer);
        response.writeInt(this.id);
        response.writeString(this.name);
    }

    @Override
    public short getHeader() {
        return Outgoing.CreateRoomMessageComposer;
    }
}