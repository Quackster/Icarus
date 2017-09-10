package org.alexdev.icarus.messages.outgoing.room.settings;

import java.util.List;

import org.alexdev.icarus.game.player.PlayerManager;
import org.alexdev.icarus.messages.MessageComposer;
import org.alexdev.icarus.messages.headers.Outgoing;

public class RoomRightsListComposer extends MessageComposer {

    private int roomId;
    private List<Integer> rights;

    public RoomRightsListComposer(int roomId, List<Integer> rights) {
        this.roomId = roomId;
        this.rights = rights;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.RoomRightsListComposer);
        this.response.writeInt(this.roomId);
        this.response.writeInt(this.rights.size());
        
        for (Integer userId : this.rights) {     
            this.response.writeInt(userId);
            this.response.writeString(PlayerManager.getPlayerData(userId).getName());
        }
    }

}
