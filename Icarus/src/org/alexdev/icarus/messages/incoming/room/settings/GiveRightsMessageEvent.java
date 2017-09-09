package org.alexdev.icarus.messages.incoming.room.settings;

import org.alexdev.icarus.dao.mysql.room.RoomDao;
import org.alexdev.icarus.game.entity.EntityStatus;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.player.PlayerManager;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.outgoing.room.RoomRightsLevelMessageComposer;
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

        int userID = reader.readInt();

        if (room.hasRights(userID, false)) {
            return;
        }

        Player user = PlayerManager.getByID(userID);

        if (user != null) {
            if (user.getRoomUser().getRoomID() == room.getData().getID()) {

                user.getRoomUser().setStatus(EntityStatus.FLAT_CONTROL, "1");
                user.getRoomUser().setNeedsUpdate(true);

                user.send(new RoomRightsLevelMessageComposer(1));
            }
        }

        room.getRights().add(userID);
        RoomDao.addRoomRights(room.getData().getID(), userID);
        
        player.send(new RightsAssignedComposer(room.getData().getID(), userID, PlayerManager.getPlayerData(userID).getName()));
    }
}
