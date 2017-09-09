package org.alexdev.icarus.messages.outgoing.user;

import org.alexdev.icarus.messages.MessageComposer;
import org.alexdev.icarus.messages.headers.Outgoing;

public class HomeRoomMessageComposer extends MessageComposer {

    private int roomID;
    private boolean forceEnter;

    public HomeRoomMessageComposer(int roomID, boolean forceEnter) {
        this.roomID = roomID;
        this.forceEnter = forceEnter;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.HomeRoomMessageComposer);
        this.response.writeInt(this.roomID); 
        this.response.writeInt(this.forceEnter); 
    }

}
