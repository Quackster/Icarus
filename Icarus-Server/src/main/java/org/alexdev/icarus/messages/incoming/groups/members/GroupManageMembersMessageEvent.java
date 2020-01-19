package org.alexdev.icarus.messages.incoming.groups.members;

import java.util.ArrayList;
import java.util.List;

import org.alexdev.icarus.game.groups.Group;
import org.alexdev.icarus.game.groups.GroupManager;
import org.alexdev.icarus.game.groups.members.GroupMemberType;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.outgoing.groups.GroupManageMembersComposer;
import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class GroupManageMembersMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {
        Group group = GroupManager.getInstance().getGroup(reader.readInt());

        if (group == null) {
            return;
        }

        int page = reader.readInt();
        String searchQuery = reader.readString();
        int requestType = reader.readInt();

        List<Integer> members = null;

        switch (requestType) {
        default:
            members = new ArrayList<>(group.getMemberManager().getMembers());
            break;
        case 1:
            members = new ArrayList<>(group.getMemberManager().getMembersByType(GroupMemberType.ADMINISTRATOR));
            break;
        case 2:
            members = new ArrayList<>(group.getMemberManager().getMembersByType(GroupMemberType.REQUEST));
            break;
        }

        boolean hasAccess = group.getOwnerId() == player.getEntityId();

        if (!hasAccess) {
            hasAccess = group.getMemberManager().isMemberType(player.getEntityId(), GroupMemberType.ADMINISTRATOR);
        }

        player.send(new GroupManageMembersComposer(group, page, requestType, searchQuery, members, hasAccess));
    }
}
