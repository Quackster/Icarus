package org.alexdev.icarus.messages.outgoing.groups.members;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.headers.Outgoing;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.messages.Response;

public class GroupMembershipRequestedComposer extends MessageComposer {

    private int groupId;
    private Player player;

    public GroupMembershipRequestedComposer(int groupId, Player player) {
        this.groupId = groupId;
        this.player = player;
    }

    @Override
    public void compose(Response response) {
        //response.init(Outgoing.GroupMembershipRequestedComposer);
        response.writeInt(this.groupId);
        response.writeInt(3);
        response.writeInt(this.player.getEntityId());
        response.writeString(this.player.getDetails().getName());
        response.writeString(this.player.getDetails().getFigure());
        response.writeString("");
    }

    @Override
    public short getHeader() {
        return Outgoing.GroupMembershipRequestedComposer;
    }
}