package org.alexdev.icarus.messages.incoming.groups;

import org.alexdev.icarus.dao.mysql.groups.GroupDao;
import org.alexdev.icarus.game.groups.Group;
import org.alexdev.icarus.game.groups.GroupManager;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.outgoing.groups.GroupInfoComposer;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class GroupInfoMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {
        
        int groupId = reader.readInt();
        
        if (!(groupId > 0)) {
            return;
        }
        
        Group group = GroupManager.getGroup(groupId);
        
        if (group == null) {
            return;
        }
        
        player.send(new GroupInfoComposer(group, player, reader.readBoolean()));
    }

}
