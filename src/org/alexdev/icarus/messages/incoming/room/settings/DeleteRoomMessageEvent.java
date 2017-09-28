package org.alexdev.icarus.messages.incoming.room.settings;

import org.alexdev.icarus.dao.mysql.room.RoomDao;
import org.alexdev.icarus.dao.mysql.room.RoomModelDao;
import org.alexdev.icarus.game.inventory.InventoryNotification;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.RoomManager;
import org.alexdev.icarus.game.room.enums.RoomAction;
import org.alexdev.icarus.messages.types.MessageEvent;
import org.alexdev.icarus.server.api.messages.ClientMessage;

public class DeleteRoomMessageEvent implements MessageEvent {

    @Override
    public void handle(Player player, ClientMessage request) {
        
        int roomId = request.readInt();
        
        Room room = RoomManager.getByRoomId(roomId);

        if (room == null) {
            return;
        }
        
        if (!room.hasOwnership(player.getEntityId()) && !player.getDetails().hasPermission("room_all_rights")) {
            return;
        }
        
        for (Player users : room.getEntityManager().getPlayers()) {
            users.performRoomAction(RoomAction.LEAVE_ROOM, true);
        }
        
        for (Item item : room.getItemManager().getItems().values()) {
            
            item.setRoomId(0);
            item.save();
            
            player.getInventory().addItem(item, InventoryNotification.NONE);
        }
        
        RoomDao.deleteRoom(roomId);
        RoomModelDao.deleteCustomModel(roomId);
        RoomManager.removeRoom(roomId);
    }
}
