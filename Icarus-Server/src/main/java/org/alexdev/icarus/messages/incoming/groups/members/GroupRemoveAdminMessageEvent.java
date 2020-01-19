package org.alexdev.icarus.messages.incoming.groups.members;

import org.alexdev.icarus.dao.mysql.room.RoomDao;
import org.alexdev.icarus.game.groups.Group;
import org.alexdev.icarus.game.groups.GroupManager;
import org.alexdev.icarus.game.groups.members.GroupMemberType;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.player.PlayerManager;
import org.alexdev.icarus.game.util.RoomUtil;
import org.alexdev.icarus.messages.outgoing.groups.members.GroupUpdateMemberComposer;
import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;
import org.alexdev.icarus.util.locale.Locale;

public class GroupRemoveAdminMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {
        Group group = GroupManager.getInstance().getGroup(reader.readInt());

        if (group == null) {
            return;
        }
        
        boolean isOwner = group.getOwnerId() == player.getEntityId();
   
        if (!isOwner) {
            player.sendMessage(Locale.getInstance().getEntry("group.only.creators.remove.admin"));
            return;
        }
        
        int userId = reader.readInt();
        group.getMemberManager().addMember(GroupMemberType.MEMBER, userId);

        Player user = PlayerManager.getInstance().getById(userId);

        if (user != null) {
            RoomUtil.refreshRights(RoomDao.getRoom(group.getRoomId(), false), user);
            user.getRoomUser().setNeedsUpdate(true);
        }

        player.send(new GroupUpdateMemberComposer(group.getId(), userId, 2));
    }
}
