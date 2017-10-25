package org.alexdev.icarus.messages.incoming.groups.edit;

import org.alexdev.icarus.game.groups.Group;
import org.alexdev.icarus.game.groups.GroupManager;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.outgoing.groups.GroupInfoMessageComposer;
import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class EditGroupTextMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {
       
        int groupId = reader.readInt();
        
        Group group = GroupManager.getInstance().getGroup(groupId);
        
        if (group == null) {
            return;
        }

        if (group.getOwnerId() != player.getEntityId()) {
            return;
        }
        
        String title = reader.readString();
        String description = reader.readString();
       
        group.setTitle(title);
        group.setDescription(description);
        group.save();
        
        player.send(new GroupInfoMessageComposer(group, player, false));
    }
}
