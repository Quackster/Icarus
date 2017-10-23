package org.alexdev.icarus.messages.incoming.groups.members;

import java.util.List;

import org.alexdev.icarus.game.groups.Group;
import org.alexdev.icarus.game.groups.GroupManager;
import org.alexdev.icarus.game.groups.access.GroupAccessType;
import org.alexdev.icarus.game.groups.members.GroupMemberType;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.player.PlayerManager;
import org.alexdev.icarus.messages.outgoing.groups.GroupInfoMessageComposer;
import org.alexdev.icarus.messages.outgoing.groups.RefreshFavouriteGroupComposer;
import org.alexdev.icarus.messages.outgoing.groups.members.GroupMembershipRequestedComposer;
import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class GroupMembershipRequestMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {

        Group group = GroupManager.getInstance().getGroup(reader.readInt());

        if (group == null) {
            return;
        }
        
        if (group.getMemberManager().isMember(player.getEntityId())) {
            return;
        }

        if (group.getAccessType() == GroupAccessType.PRIVATE) {
            return;
        }

        if (group.getAccessType() == GroupAccessType.LOCKED) {
            group.getMemberManager().addMember(GroupMemberType.REQUEST, player.getEntityId());

            List<Integer> groupOfficials = group.getMemberManager().getMembersByType(GroupMemberType.ADMINISTRATOR);
            groupOfficials.add(group.getOwnerId());

            for (int userId : groupOfficials) {
                
                Player user = PlayerManager.getById(userId);
                
                if (user != null) {             
                    user.send(new GroupMembershipRequestedComposer(group.getId(), player));
                }
            }
        }

        if (group.getAccessType() == GroupAccessType.OPEN) {
            group.getMemberManager().addMember(GroupMemberType.MEMBER, player.getEntityId());
            player.send(new RefreshFavouriteGroupComposer(player.getEntityId()));
        }
        
        player.send(new GroupInfoMessageComposer(group, player, false));
    }
}
