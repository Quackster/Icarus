package org.alexdev.icarus.messages.incoming.groups.edit;

import org.alexdev.icarus.dao.mysql.groups.GroupDao;
import org.alexdev.icarus.game.groups.Group;
import org.alexdev.icarus.game.groups.access.GroupAccessType;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.outgoing.groups.GroupInfoComposer;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class EditGroupAccessMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {
        
        int groupId = reader.readInt();
        
        Group group = GroupDao.getGroup(groupId);
        
        if (group == null) {
            return;
        }

        if (group.getOwnerId() != player.getDetails().getId()) {
            return;
        }
        
        GroupAccessType accessType = GroupAccessType.getTypeById(reader.readInt());
        boolean canMembersDecorate = reader.readIntAsBool();
        
        group.setAccessType(accessType);
        group.setCanMembersDecorate(canMembersDecorate);
        group.save();
        
        player.send(new GroupInfoComposer(group, player, false));
    }

}
