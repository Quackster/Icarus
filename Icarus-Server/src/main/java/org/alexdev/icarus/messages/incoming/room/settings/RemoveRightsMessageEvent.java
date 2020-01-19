package org.alexdev.icarus.messages.incoming.room.settings;

import org.alexdev.icarus.dao.mysql.room.RoomDao;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.player.PlayerManager;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.util.RoomUtil;
import org.alexdev.icarus.messages.outgoing.room.settings.RightsRemovedMessageComposer;
import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class RemoveRightsMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {
        Room room = player.getRoomUser().getRoom();

        if (room == null) {
            return;
        }
        
        if (!room.hasOwnership(player.getEntityId()) && !player.hasPermission("room_all_rights")) {
            return;
        }
        
        int amount = reader.readInt();
        
        for (int i = 0; i < amount; i++) {
            
            int userId = reader.readInt();
            
            if (!room.hasRights(userId)) {
                continue;
            }

            room.getRights().remove(Integer.valueOf(userId));
            RoomDao.removeRoomRights(room.getData().getId(), userId);

            Player user = PlayerManager.getInstance().getById(userId);
            
            if (user != null) {
                if (user.getRoomUser().getRoomId() == room.getData().getId()) {
                    //user.getRoomUser().removeStatus(EntityStatus.FLAT_CONTROL);
                    //user.getRoomUser().setNeedsUpdate(true);
                    RoomUtil.refreshRights(room, user);
                    user.getRoomUser().setNeedsUpdate(true);
                }
            }
            
            player.send(new RightsRemovedMessageComposer(room.getData().getId(), userId));
        }
    }
}
