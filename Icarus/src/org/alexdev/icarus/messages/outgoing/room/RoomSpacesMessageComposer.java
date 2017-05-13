package org.alexdev.icarus.messages.outgoing.room;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;

public class RoomSpacesMessageComposer extends OutgoingMessageComposer {

    private String space;
    private String data;

    public RoomSpacesMessageComposer(String space, String data) {
        this.space = space;
        this.data = data;
    }

    @Override
    public void write() {
        response.init(Outgoing.RoomSpacesMessageComposer);
        response.writeString(this.space);
        response.writeString(this.data);
    }

}
