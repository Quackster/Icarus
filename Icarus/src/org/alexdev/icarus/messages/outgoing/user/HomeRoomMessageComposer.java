package org.alexdev.icarus.messages.outgoing.user;

import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.MessageComposer;

public class HomeRoomMessageComposer extends MessageComposer {

    private int roomId;
    private boolean forceEnter;

    public HomeRoomMessageComposer(int roomId, boolean forceEnter) {
        this.roomId = roomId;
        this.forceEnter = forceEnter;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.HomeRoomMessageComposer);
        this.response.writeInt(this.roomId); 
        this.response.writeInt(this.forceEnter); 
    }

}
