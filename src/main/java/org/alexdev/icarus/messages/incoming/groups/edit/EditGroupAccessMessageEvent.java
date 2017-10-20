package org.alexdev.icarus.messages.incoming.groups.edit;

import org.alexdev.icarus.game.groups.Group;
import org.alexdev.icarus.game.groups.GroupManager;
import org.alexdev.icarus.game.groups.access.GroupAccessType;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.outgoing.groups.GroupInfoMessageComposer;
import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class EditGroupAccessMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {
        
        int groupId = reader.readInt();
        
        Group group = GroupManager.getGroup(groupId);
        
        if (group == null) {
            return;
        }

        if (group.getOwnerId() != player.getEntityId()) {
            return;
        }
        
        GroupAccessType accessType = GroupAccessType.getTypeById(reader.readInt());
        boolean canMembersDecorate = !reader.readIntAsBool();
        
        group.setAccessType(accessType);
        group.setCanMembersDecorate(canMembersDecorate);
        group.save();
        
        player.send(new GroupInfoMessageComposer(group, player, false));
    }

}
