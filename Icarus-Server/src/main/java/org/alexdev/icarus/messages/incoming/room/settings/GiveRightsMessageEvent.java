package org.alexdev.icarus.messages.incoming.room.settings;

import org.alexdev.icarus.dao.mysql.room.RoomDao;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.player.PlayerManager;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.util.RoomUtil;
import org.alexdev.icarus.messages.outgoing.room.settings.RightsAssignedMessageComposer;
import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class GiveRightsMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {

        Room room = player.getRoomUser().getRoom();

        if (room == null) {
            return;
        }

        if (!room.hasOwnership(player.getEntityId())&& !player.hasPermission("room_all_rights")) {
            return;
        }

        int userId = reader.readInt();

        if (room.hasRights(userId)) {
            return;
        }

        room.getRights().add(userId);
        RoomDao.addRoomRights(room.getData().getId(), userId);

        Player user = PlayerManager.getInstance().getById(userId);

        if (user != null) {
            if (user.getRoomUser().getRoomId() == room.getData().getId()) {
                //user.getRoomUser().setStatus(EntityStatus.FLAT_CONTROL, "1");
                //user.getRoomUser().setNeedsUpdate(true);
                RoomUtil.refreshRights(room, user);
            }
        }

        
        player.send(new RightsAssignedMessageComposer(room.getData().getId(), userId, PlayerManager.getInstance().getPlayerData(userId).getName()));
    }
}
