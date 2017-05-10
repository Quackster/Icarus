package org.alexdev.icarus.messages.incoming.handshake;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.server.messages.AbstractReader;

public class UniqueIDMessageEvent implements MessageEvent {

	@Override
	public void handle(Player player, AbstractReader request) {

		request.readString();
		player.setMachineId(request.readString());
	}

}
