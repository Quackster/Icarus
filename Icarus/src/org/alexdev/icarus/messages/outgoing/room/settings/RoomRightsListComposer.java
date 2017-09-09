package org.alexdev.icarus.messages.outgoing.room.settings;

import java.util.List;

import org.alexdev.icarus.game.player.PlayerManager;
import org.alexdev.icarus.messages.MessageComposer;
import org.alexdev.icarus.messages.headers.Outgoing;

public class RoomRightsListComposer extends MessageComposer {

    private int roomID;
    private List<Integer> rights;

    public RoomRightsListComposer(int roomID, List<Integer> rights) {
        this.roomID = roomID;
        this.rights = rights;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.RoomRightsListComposer);
        this.response.writeInt(this.roomID);
        this.response.writeInt(this.rights.size());
        
        for (Integer userID : this.rights) {     
            this.response.writeInt(userID);
            this.response.writeString(PlayerManager.getPlayerData(userID).getName());
        }
    }

}
