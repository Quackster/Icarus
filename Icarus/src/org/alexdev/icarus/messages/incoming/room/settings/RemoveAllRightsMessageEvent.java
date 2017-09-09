package org.alexdev.icarus.messages.incoming.room.settings;

import org.alexdev.icarus.dao.mysql.room.RoomDao;
import org.alexdev.icarus.game.entity.EntityStatus;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.player.PlayerManager;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.outgoing.room.RoomRightsLevelMessageComposer;
import org.alexdev.icarus.messages.outgoing.room.settings.RightsRemovedComposer;
import org.alexdev.icarus.server.api.messages.ClientMessage;

import com.google.common.collect.Lists;

public class RemoveAllRightsMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage reader) {
        
        Room room = player.getRoomUser().getRoom();

        if (room == null) {
            return;
        }

        if (!room.hasRights(player, true)) {
            return;
        }
        
        for (int userID : room.getRights()) {
            
            Player user = PlayerManager.getByID(userID);
            
            if (user != null) {
                if (user.getRoomUser().getRoomID() == room.getData().getID()) {
                    
                    user.getRoomUser().removeStatus(EntityStatus.FLAT_CONTROL);
                    user.getRoomUser().setNeedsUpdate(true);
                    
                    user.send(new RoomRightsLevelMessageComposer(0));
                }
            }
            
            player.send(new RightsRemovedComposer(room.getData().getID(), userID));
        }
        
        room.getRights().clear();
        
        RoomDao.saveRoomRights(room.getData().getID(), Lists.newArrayList());
    }

}
