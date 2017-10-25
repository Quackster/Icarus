package org.alexdev.icarus.messages.outgoing.room;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class RoomModelMessageComposer extends MessageComposer {

    private String model;
    private int id;

    public RoomModelMessageComposer(String model, int id) {
        this.model = model;
        this.id = id;
    }

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.InitialRoomInfoMessageComposer);
        response.writeString(this.model);
        response.writeInt(this.id);
    }

    @Override
    public short getHeader() {
        return Outgoing.InitialRoomInfoMessageComposer;
    }
}