package org.alexdev.icarus.messages.outgoing.room;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.MessageComposer;

public class RoomModelMessageComposer extends MessageComposer {
    
    private String model;
    private int id;

    public RoomModelMessageComposer(String model, int id) {
        this.model = model;
        this.id = id;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.InitialRoomInfoMessageComposer);
        this.response.writeString(this.model);
        this.response.writeInt(this.id);
    }
}
