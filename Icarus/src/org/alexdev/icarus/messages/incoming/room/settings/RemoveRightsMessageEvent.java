package org.alexdev.icarus.messages.incoming.room.settings;

import org.alexdev.icarus.dao.mysql.room.RoomDao;
import org.alexdev.icarus.game.entity.EntityStatus;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.player.PlayerManager;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.outgoing.room.RightsLevelMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.settings.RightsRemovedComposer;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class RemoveRightsMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {
        
        Room room = player.getRoomUser().getRoom();

        if (room == null) {
            return;
        }
        
        if (!room.hasRights(player, true)) {
            return;
        }
        
        int amount = reader.readInt();
        
        for (int i = 0; i < amount; i++) {
            
            int userID = reader.readInt();
            
            if (!room.hasRights(userID, false)) {
                continue;
            }
            
            Player user = PlayerManager.getByID(userID);
            
            if (user != null) {
                if (user.getRoomUser().getRoomID() == room.getData().getID()) {
                    
                    user.getRoomUser().removeStatus(EntityStatus.FLAT_CONTROL);
                    user.getRoomUser().setNeedsUpdate(true);
                    
                    user.send(new RightsLevelMessageComposer(0));
                }
            }
            
            room.getRights().remove(Integer.valueOf(userID));
            RoomDao.removeRoomRights(room.getData().getID(), userID);
            
            player.send(new RightsRemovedComposer(room.getData().getID(), userID));
        }
    }
}
