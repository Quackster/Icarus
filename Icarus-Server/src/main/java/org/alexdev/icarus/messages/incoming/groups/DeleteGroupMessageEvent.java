package org.alexdev.icarus.messages.incoming.groups;

import org.alexdev.icarus.dao.mysql.room.RoomDao;
import org.alexdev.icarus.game.groups.Group;
import org.alexdev.icarus.game.groups.GroupManager;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.enums.RoomAction;
import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class DeleteGroupMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {

        int groupId = reader.readInt();
        
        if (!(groupId > 0)) {
            return;
        }
        
        Group group = GroupManager.getInstance().getGroup(groupId);
        
        if (group == null) {
            return;
        }
        
        if (group.getOwnerId() != player.getEntityId()) {
            return;
        }
        
        Room room = RoomDao.getRoom(group.getRoomId(), false);
        
        room.getData().setGroupId(0);
        room.unloadGroup();
        room.save();
        
        group.delete();
        
        for (Player users : room.getEntityManager().getPlayers()) {
            users.performRoomAction(RoomAction.LEAVE_ROOM, true);
        }
    }

}
