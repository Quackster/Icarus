package org.alexdev.icarus.messages.incoming.groups.members;

import org.alexdev.icarus.game.groups.Group;
import org.alexdev.icarus.game.groups.GroupManager;
import org.alexdev.icarus.game.groups.members.GroupMemberType;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.player.PlayerManager;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.outgoing.groups.GroupInfoComposer;
import org.alexdev.icarus.messages.outgoing.groups.GroupManageMembersComposer;
import org.alexdev.icarus.messages.outgoing.groups.members.GroupUpdateMemberComposer;
import org.alexdev.icarus.server.api.messages.ClientMessage;
import org.alexdev.icarus.util.Util;

public class GroupRemoveMemberMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {
        
        Group group = GroupManager.getGroup(reader.readInt());

        if (group == null) {
            return;
        }
        
        boolean isOwner = group.getOwnerId() == player.getDetails().getId();
        boolean isAdmin = group.getMemberManager().isMemberType(player.getDetails().getId(), GroupMemberType.ADMINISTRATOR);
   
        if (!isOwner && !isAdmin) {
            return;
        }
        
        int removeUserId = reader.readInt();
        
        if (!isOwner && group.getMemberManager().isMemberType(removeUserId, GroupMemberType.ADMINISTRATOR)) {
            player.sendMessage(Util.getLocale("group.remove.administrator.denied"));
            return;
        }
       
        group.getMemberManager().remove(removeUserId);
        
        Player user = PlayerManager.getById(removeUserId);
        
        if (user != null) {
            user.send(new GroupInfoComposer(group, user, false));
        }
        
        player.send(new GroupUpdateMemberComposer(group.getId(), removeUserId, 0));
    }
}
