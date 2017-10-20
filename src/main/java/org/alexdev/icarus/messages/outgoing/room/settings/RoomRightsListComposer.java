package org.alexdev.icarus.messages.outgoing.room.settings;

import java.util.List;

import org.alexdev.icarus.game.player.PlayerManager;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class RoomRightsListComposer extends MessageComposer {

    private int roomId;
    private List<Integer> rights;

    public RoomRightsListComposer(int roomId, List<Integer> rights) {
        this.roomId = roomId;
        this.rights = rights;
    }

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.RoomRightsListComposer);
        response.writeInt(this.roomId);
        response.writeInt(this.rights.size());

        for (Integer userId : this.rights) {
            response.writeInt(userId);
            response.writeString(PlayerManager.getPlayerData(userId).getName());
        }
    }

    @Override
    public short getHeader() {
        return Outgoing.RoomRightsListComposer;
    }
}