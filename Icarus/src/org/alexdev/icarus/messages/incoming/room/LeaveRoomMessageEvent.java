package org.alexdev.icarus.messages.incoming.room;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.server.messages.AbstractReader;

public class LeaveRoomMessageEvent implements MessageEvent {

	@Override
	public void handle(Player player, AbstractReader request) {

		Room room = player.getRoomUser().getRoom();
		
		if (room == null) {
			return;
		}

		room.leaveRoom(player, false);
	}

}
