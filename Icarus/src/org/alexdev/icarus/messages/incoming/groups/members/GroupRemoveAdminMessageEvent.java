package org.alexdev.icarus.messages.incoming.groups.members;

import org.alexdev.icarus.game.groups.Group;
import org.alexdev.icarus.game.groups.GroupManager;
import org.alexdev.icarus.game.groups.members.GroupMemberType;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.outgoing.groups.members.GroupUpdateMemberComposer;
import org.alexdev.icarus.server.api.messages.ClientMessage;
import org.alexdev.icarus.util.Util;

public class GroupRemoveAdminMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {
        
        Group group = GroupManager.getGroup(reader.readInt());

        if (group == null) {
            return;
        }
        
        boolean isOwner = group.getOwnerId() == player.getDetails().getId();
   
        if (!isOwner) {
            player.sendMessage(Util.getLocale("group.only.creators.remove.admin"));
            return;
        }
        
        int userId = reader.readInt();
        
        group.getMemberManager().addMember(GroupMemberType.MEMBER, userId);
        
        player.send(new GroupUpdateMemberComposer(group.getId(), userId, 2));
    }
}
