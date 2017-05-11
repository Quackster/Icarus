package org.alexdev.icarus.messages.incoming.messenger;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.messages.MessageEvent;
import org.alexdev.icarus.server.api.messages.AbstractReader;

public class MessengerUpdateMessageEvent implements MessageEvent {

	@Override
	public void handle(Player player, AbstractReader request) {

		// update messeger ok
	}
}
