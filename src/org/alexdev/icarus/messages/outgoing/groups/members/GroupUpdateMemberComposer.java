package org.alexdev.icarus.messages.outgoing.groups.members;

import org.alexdev.icarus.game.player.PlayerDetails;
import org.alexdev.icarus.game.player.PlayerManager;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;

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
    public void write() {
        this.response.init(Outgoing.GroupUpdateMemberComposer);
        this.response.writeInt(this.groupId);
        this.response.writeInt(this.type);
        this.response.writeInt(this.details.getId());
        this.response.writeString(this.details.getName());
        this.response.writeString(this.details.getFigure());
        this.response.writeString("");
    }

}
