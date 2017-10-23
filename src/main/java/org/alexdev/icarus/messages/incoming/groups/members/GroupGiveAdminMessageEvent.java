package org.alexdev.icarus.messages.incoming.groups.members;

import org.alexdev.icarus.game.groups.Group;
import org.alexdev.icarus.game.groups.GroupManager;
import org.alexdev.icarus.game.groups.members.GroupMemberType;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.outgoing.groups.members.GroupUpdateMemberComposer;
import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;
import org.alexdev.icarus.util.Util;

public class GroupGiveAdminMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {
        
        Group group = GroupManager.getInstance().getGroup(reader.readInt());

        if (group == null) {
            return;
        }
        
        boolean isOwner = group.getOwnerId() == player.getEntityId();
   
        if (!isOwner) {
            player.sendMessage(Util.getLocaleEntry("group.only.creators"));
            return;
        }
        
        int userId = reader.readInt();
        group.getMemberManager().addMember(GroupMemberType.ADMINISTRATOR, userId);
        player.send(new GroupUpdateMemberComposer(group.getId(), userId, 1));
    }

}
