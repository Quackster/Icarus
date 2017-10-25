package org.alexdev.icarus.messages.outgoing.room;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class RoomSpacesMessageComposer extends MessageComposer {

    private String space;
    private String data;

    public RoomSpacesMessageComposer(String space, String data) {
        this.space = space;
        this.data = data;
    }

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.RoomSpacesMessageComposer);
        response.writeString(this.space);
        response.writeString(this.data);
    }

    @Override
    public short getHeader() {
        return Outgoing.RoomSpacesMessageComposer;
    }
}