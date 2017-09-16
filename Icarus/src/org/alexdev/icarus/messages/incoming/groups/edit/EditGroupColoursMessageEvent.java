package org.alexdev.icarus.messages.incoming.groups.edit;

import org.alexdev.icarus.dao.mysql.groups.GroupDao;
import org.alexdev.icarus.game.groups.Group;
import org.alexdev.icarus.game.groups.GroupManager;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.outgoing.groups.GroupInfoComposer;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class EditGroupColoursMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {
        
        int groupId = reader.readInt();
        
        Group group = GroupManager.getGroup(groupId);
        
        if (group == null) {
            return;
        }

        if (group.getOwnerId() != player.getDetails().getId()) {
            return;
        }
        
        int colourA = reader.readInt();
        int colourB = reader.readInt();
        
        group.setColourA(colourA);
        group.setColourB(colourB);
        group.save();
        
        player.send(new GroupInfoComposer(group, player, false));
    }
}
