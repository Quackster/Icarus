package org.alexdev.icarus.messages.incoming.room.user;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.player.RoomUser;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.server.messages.AbstractReader;

public class ShoutMessageEvent implements MessageEvent {

	@Override
	public void handle(Player player, AbstractReader request) {
		
		RoomUser roomUser = player.getRoomUser();

		if (roomUser == null) {
			return;
		}

		Room room = roomUser.getRoom();

		if (room == null) {
			return;
		}
		
		roomUser.chat(request.readString(), request.readInt(),  request.readInt(), true, true);
	}

}
