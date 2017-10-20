package org.alexdev.icarus.messages.outgoing.groups.members;

import org.alexdev.icarus.game.player.PlayerDetails;
import org.alexdev.icarus.game.player.PlayerManager;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class GroupUpdateMemberComposer extends MessageComposer {

    private int groupId;
    private PlayerDetails details;
    private int type;

    public GroupUpdateMemberComposer(int groupId, int userId, int type) {
        this.groupId = groupId;
        this.details = PlayerManager.getPlayerData(userId);
        this.type = type;
    }

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.GroupUpdateMemberComposer);
        response.writeInt(this.groupId);
        response.writeInt(this.type);
        response.writeInt(this.details.getId());
        response.writeString(this.details.getName());
        response.writeString(this.details.getFigure());
        response.writeString("");
    }

    @Override
    public short getHeader() {
        return Outgoing.GroupUpdateMemberComposer;
    }
}
