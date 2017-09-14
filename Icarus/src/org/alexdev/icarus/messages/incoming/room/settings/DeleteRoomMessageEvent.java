package org.alexdev.icarus.messages.incoming.room.settings;

import org.alexdev.icarus.dao.mysql.room.RoomDao;
import org.alexdev.icarus.dao.mysql.room.RoomModelDao;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.enums.RoomAction;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class DeleteRoomMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage request) {
        
        Room room = player.getRoomUser().getRoom();

        if (room == null) {
            return;
        }
        
        if (!room.hasRights(player.getDetails().getId(), true)) {
            return;
        }
        
        int roomId = request.readInt(); // room id
        
        for (Player users : room.getEntityManager().getPlayers()) {
            users.performRoomAction(RoomAction.LEAVE_ROOM, true);
        }
        
        for (Item item : room.getItemManager().getItems().values()) {
            
            item.setRoomId(0);
            item.save();
            
            player.getInventory().addItem(item);
        }
        
        RoomDao.deleteRoom(room);
        RoomModelDao.deleteCustomModel(roomId);
        
        room.dispose(true);
    }
}
