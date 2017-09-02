package org.alexdev.icarus.game.commands;

import org.alexdev.icarus.game.player.Player;

public interface Command {

	public void handleCommand(Player player, String message);
}
