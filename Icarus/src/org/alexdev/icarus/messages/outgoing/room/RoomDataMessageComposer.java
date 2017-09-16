package org.alexdev.icarus.messages.outgoing.room;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.util.RoomUtil;
import org.alexdev.icarus.messages.MessageComposer;
import org.alexdev.icarus.messages.headers.Outgoing;

public class RoomDataMessageComposer extends MessageComposer {

    private Room room;
    private Player player;
    private boolean isLoading;
    private boolean checkEntry;

    public RoomDataMessageComposer(Room room, Player player, boolean isLoading, boolean checkEntry) {
        this.room = room;
        this.player = player;
        this.isLoading = isLoading;
        this.checkEntry = checkEntry;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.RoomDataMessageComposer);
        this.response.writeBool(this.isLoading);
        RoomUtil.serialise(this.room, this.response, this.isLoading);
        this.response.writeBool(this.checkEntry);
        this.response.writeBool(false); 
        this.response.writeBool(false);
        this.response.writeBool(false);
        this.response.writeInt(room.getData().getWhoCanMute());
        this.response.writeInt(room.getData().getWhoCanKick());
        this.response.writeInt(room.getData().getWhoCanBan());
        this.response.writeBool(room.hasRights(player.getDetails().getId(), true));
        this.response.writeInt(room.getData().getBubbleMode());
        this.response.writeInt(room.getData().getBubbleType());
        this.response.writeInt(room.getData().getBubbleScroll());
        this.response.writeInt(room.getData().getChatMaxDistance());
        this.response.writeInt(room.getData().getChatFloodProtection());
        
        /*response.writeInt(room->getData()->chat_speed);
        this.response.writeInt(room->getData()->chat_flood);
        this.response.writeInt(room->getData()->chat_distance)*/;
    }
}
