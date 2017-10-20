package org.alexdev.icarus.messages.outgoing.room.user;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.alexdev.icarus.game.entity.EntityType;
import org.alexdev.icarus.game.room.user.ChatType;
import org.alexdev.icarus.game.room.user.RoomUser;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class TalkMessageComposer extends MessageComposer {

    private RoomUser roomUser;
    private ChatType type;
    private String message;
    private int textColour;

    public TalkMessageComposer(RoomUser roomUser, ChatType type, String message, int bubble) {
        this.roomUser = roomUser;
        this.type = type;
        this.message = message;
        this.textColour = bubble;
    }

    @Override
    public void compose(Response response) {

        if (roomUser.getEntity().getType() == EntityType.PLAYER) {
            List<Integer> allowedColours = new ArrayList<Integer>(Arrays.asList(new Integer[]{0, 3, 4, 5, 6, 7, 9, 10, 11, 12, 13, 14, 15, 16, 17, 19, 20, 29}));

            if (!allowedColours.contains(this.textColour)) {
                this.textColour = 0;
            }
        }

        //response.init(this.type.getHeader());
        response.writeInt(this.roomUser.getVirtualId());
        response.writeString(this.message);
        response.writeInt(0);
        response.writeInt(this.textColour);
        response.writeInt(0);
        response.writeInt(1);
    }

    @Override
    public short getHeader() {
        return this.type.getHeader();
    }
}