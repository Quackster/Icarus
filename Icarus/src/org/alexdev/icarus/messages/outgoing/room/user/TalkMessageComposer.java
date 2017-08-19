package org.alexdev.icarus.messages.outgoing.room.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.alexdev.icarus.game.room.RoomUser;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.parsers.OutgoingMessageComposer;

public class TalkMessageComposer extends OutgoingMessageComposer {

    private RoomUser roomUser;
    private boolean shout;
    private String message;
    private int count;
    private int textColour;

    public TalkMessageComposer(RoomUser roomUser, boolean shout, String message, int count, int textColour) {
        this.roomUser = roomUser;
        this.shout = shout;
        this.message = message;
        this.count = count;
        this.textColour = textColour;
    }

    @Override
    public void write() {
    
        List<Integer> allowedColours = new ArrayList<Integer>(Arrays.asList(new Integer[] { 0, 3, 4, 5, 6, 7, 9, 10, 11, 12, 13, 14, 15, 16, 17, 19, 20, 29 }));

        if (!allowedColours.contains(this.textColour)) {
            this.textColour = 0;
        }

        int header = Outgoing.ChatMessageComposer;

        if (this.shout) {
            header = Outgoing.ShoutMessageComposer;
        }

        response.init(header);
        response.writeInt(this.roomUser.getVirtualId());
        response.writeString(this.message);
        response.writeInt(0);
        response.writeInt(this.textColour);
        response.writeInt(0);
        response.writeInt(this.count);
    }
}
