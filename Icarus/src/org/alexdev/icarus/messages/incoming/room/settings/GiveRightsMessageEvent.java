package org.alexdev.icarus.messages.incoming.room.settings;

import org.alexdev.icarus.dao.mysql.room.RoomDao;
import org.alexdev.icarus.game.entity.EntityStatus;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.player.PlayerManager;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.outgoing.room.RightsLevelMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.settings.RightsAssignedComposer;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class GiveRightsMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {

        Room room = player.getRoomUser().getRoom();

        if (room == null) {
            return;
        }

        if (!room.hasRights(player, true)) {
            return;
        }

        int userId = reader.readInt();

        if (room.hasRights(userId, false)) {
            return;
        }

        Player user = PlayerManager.getById(userId);

        if (user != null) {
            if (user.getRoomUser().getRoomId() == room.getData().getId()) {

                user.getRoomUser().setStatus(EntityStatus.FLAT_CONTROL, "1");
                user.getRoomUser().setNeedsUpdate(true);

                user.send(new RightsLevelMessageComposer(1));
            }
        }

        room.getRights().add(userId);
        RoomDao.addRoomRights(room.getData().getId(), userId);
        
        player.send(new RightsAssignedComposer(room.getData().getId(), userId, PlayerManager.getPlayerData(userId).getName()));
    }
}
