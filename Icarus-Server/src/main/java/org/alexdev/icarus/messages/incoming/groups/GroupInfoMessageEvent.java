package org.alexdev.icarus.messages.incoming.groups;

import org.alexdev.icarus.game.groups.Group;
import org.alexdev.icarus.game.groups.GroupManager;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.outgoing.groups.GroupInfoMessageComposer;
import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class GroupInfoMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {
        Group group = GroupManager.getInstance().getGroup(reader.readInt());
        
        if (group == null) {
            return;
        }
        
        player.send(new GroupInfoMessageComposer(group, player, reader.readBoolean()));
    }

}
