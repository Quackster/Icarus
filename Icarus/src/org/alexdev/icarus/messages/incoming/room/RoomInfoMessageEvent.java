package org.alexdev.icarus.messages.incoming.room;

import org.alexdev.icarus.dao.mysql.RoomDao;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.player.RoomUser;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.outgoing.room.RoomDataMessageComposer;
import org.alexdev.icarus.server.messages.AbstractReader;

public class RoomInfoMessageEvent implements MessageEvent {

	@Override
	public void handle(Player player, AbstractReader request) {

		Room room = RoomDao.getRoom(request.readInt(), true);

		if (room == null) {
			return;
		}

		RoomUser roomUser = player.getRoomUser();

		boolean forwardPlayer = true;

		if (roomUser.inRoom()) {

			if (roomUser.getRoom() != room) {
				roomUser.getRoom().leaveRoom(player, false);
			} else {
				forwardPlayer = false;
			}
		}

		if (roomUser.isLoadingRoom()) {
			forwardPlayer = false;
		}

		if (forwardPlayer) {
			player.send(new RoomDataMessageComposer(room, player, request.readIntAsBool(), request.readIntAsBool()));
		}
	}

}
