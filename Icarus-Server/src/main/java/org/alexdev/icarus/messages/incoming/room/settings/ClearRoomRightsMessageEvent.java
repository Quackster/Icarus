package org.alexdev.icarus.messages.incoming.room.settings;

import org.alexdev.icarus.dao.mysql.room.RoomDao;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.player.PlayerManager;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.util.RoomUtil;
import org.alexdev.icarus.messages.outgoing.room.settings.RightsRemovedMessageComposer;
import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;;

public class ClearRoomRightsMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {
        
        Room room = player.getRoomUser().getRoom();

        if (room == null) {
            return;
        }

        if (!room.hasOwnership(player.getEntityId()) && !player.hasPermission("room_all_rights")) {
            return;
        }
        
        for (int userId : room.getRights()) {
            
            Player user = PlayerManager.getInstance().getById(userId);
            
            if (user != null) {
                if (user.getRoomUser().getRoomId() == room.getData().getId()) {
                    //user.getRoomUser().removeStatus(EntityStatus.FLAT_CONTROL);
                    //user.getRoomUser().setNeedsUpdate(true);
                    RoomUtil.refreshRights(room, user);
                    user.getRoomUser().setNeedsUpdate(true);
                }
            }
            
            player.sendQueued(new RightsRemovedMessageComposer(room.getData().getId(), userId));
        }

        player.flushQueue();
        
        room.getRights().clear();
        RoomDao.clearRoomRights(room.getData().getId());
    }
}
