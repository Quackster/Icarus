package org.alexdev.icarus.messages.incoming.room.user;

import org.alexdev.icarus.Icarus;
import org.alexdev.icarus.dao.mysql.RoomDao;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.server.messages.AbstractReader;

public class DeleteRoomMessageEvent implements MessageEvent {

	@Override
	public void handle(Player player, AbstractReader request) {
		
		Room room = player.getRoomUser().getRoom();

		if (room == null) {
			return;
		}
		
		if (!room.hasRights(player, true)) {
			return;
		}
		
		request.readInt(); // room id
		
		for (Player users : room.getUsers()) {
			room.leaveRoom(users, true);
		}
		
		RoomDao.deleteRoom(room);
		room.dispose(true);
	}

}
