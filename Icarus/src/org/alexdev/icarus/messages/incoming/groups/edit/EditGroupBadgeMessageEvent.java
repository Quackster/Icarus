package org.alexdev.icarus.messages.incoming.groups.edit;

import java.util.List;

import org.alexdev.icarus.dao.mysql.groups.GroupDao;
import org.alexdev.icarus.game.groups.Group;
import org.alexdev.icarus.game.groups.GroupManager;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.util.BadgeUtil;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.outgoing.groups.GroupInfoComposer;
import org.alexdev.icarus.server.api.messages.ClientMessage;

import com.google.common.collect.Lists;

public class EditGroupBadgeMessageEvent implements MessageEvent {

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
        
        reader.readInt();

        int groupBase = reader.readInt();
        int groupBaseColour = reader.readInt();
        
        reader.readInt();

        List<Integer> groupItems = Lists.newArrayList();

        for (int i = 0; i < 12; i++) {
            groupItems.add(reader.readInt());
        }

        String badge = BadgeUtil.generate(groupBase, groupBaseColour, groupItems);

        group.setBadge(badge);
        group.save();
        
        player.send(new GroupInfoComposer(group, player, false));
    }
}
