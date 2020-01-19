package org.alexdev.icarus.messages.outgoing.room;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.util.RoomUtil;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class RoomDataMessageComposer extends MessageComposer {
    private Room room;
    private Player player;
    private boolean roomForward;
    private boolean enterRoom;

    public RoomDataMessageComposer(Room room, Player player, boolean roomForward, boolean enterRoom) {
        this.room = room;
        this.player = player;
        this.roomForward = roomForward;
        this.enterRoom = enterRoom;
    }

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.RoomDataMessageComposer);
        response.writeBool(this.enterRoom);
        RoomUtil.serialise(this.room, response);
        response.writeBool(this.roomForward);
        response.writeBool(false);
        response.writeBool(false);
        response.writeBool(false);
        response.writeInt(room.getData().getWhoCanMute());
        response.writeInt(room.getData().getWhoCanKick());
        response.writeInt(room.getData().getWhoCanBan());
        response.writeBool(room.hasOwnership(player.getEntityId()));
        response.writeInt(room.getData().getBubbleMode());
        response.writeInt(room.getData().getBubbleType());
        response.writeInt(room.getData().getBubbleScroll());
        response.writeInt(room.getData().getChatMaxDistance());
        response.writeInt(room.getData().getChatFloodProtection());
        
        /*response.writeInt(room->getData()->chat_speed);
        response.writeInt(room->getData()->chat_flood);
        response.writeInt(room->getData()->chat_distance)*/
        ;
    }

    @Override
    public short getHeader() {
        return Outgoing.RoomDataMessageComposer;
    }
}