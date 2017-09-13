package org.alexdev.icarus.messages.incoming.groups;

import org.alexdev.icarus.dao.mysql.groups.GroupDao;
import org.alexdev.icarus.game.groups.Group;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.outgoing.groups.ManageGroupComposer;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class ManageGroupMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {
        
        Group group = GroupDao.getGroup(reader.readInt());
        
        if (group == null) {
            return;
        }
        
        player.send(new ManageGroupComposer(group));
    }
}
