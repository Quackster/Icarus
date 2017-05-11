package org.alexdev.icarus.messages;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.server.api.messages.AbstractReader;

public interface MessageEvent {
	public void handle(Player player, AbstractReader reader);
}
