package org.alexdev.icarus.messages.incoming.room.user;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.messages.outgoing.room.RoomSettingsDataMessageComposer;
import org.alexdev.icarus.server.api.messages.AbstractReader;

public class RoomSettingsDataMessageEvent implements MessageEvent {

	@Override
	public void handle(Player player, AbstractReader request) {
		
		int roomId = request.readInt();
		
		Room room = player.getRoomUser().getRoom();
		
		if (room.getData().getId() != roomId) {
			return;
		}
		
		player.send(new RoomSettingsDataMessageComposer(room));
	}

}
