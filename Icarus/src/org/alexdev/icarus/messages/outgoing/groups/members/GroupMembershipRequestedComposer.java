package org.alexdev.icarus.messages.outgoing.groups.members;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.MessageComposer;
import org.alexdev.icarus.messages.headers.Outgoing;

public class GroupMembershipRequestedComposer extends MessageComposer {

    private int groupId;
    private Player player;

    public GroupMembershipRequestedComposer(int groupId, Player player) {
        this.groupId = groupId;
        this.player = player;
    }

    @Override
    public void write() {
        this.response.init(Outgoing.GroupMembershipRequestedComposer);
        this.response.writeInt(this.groupId);
        this.response.writeInt(3);
        this.response.writeInt(this.player.getEntityId());
        this.response.writeString(this.player.getDetails().getName());
        this.response.writeString(this.player.getDetails().getFigure());
        this.response.writeString("");
    }
}
