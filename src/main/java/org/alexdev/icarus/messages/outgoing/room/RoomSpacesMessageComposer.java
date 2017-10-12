package org.alexdev.icarus.messages.outgoing.room;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;

public class RoomSpacesMessageComposer extends MessageComposer {

    private String space;
    private String data;

    public RoomSpacesMessageComposer(String space, String data) {
        this.space = space;
        this.data = data;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.RoomSpacesMessageComposer);
        this.response.writeString(this.space);
        this.response.writeString(this.data);
    }
}
