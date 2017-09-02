package org.alexdev.icarus.game.commands;

import java.util.Map;

import org.alexdev.icarus.game.commands.types.*;
import org.alexdev.icarus.game.player.Player;

import com.google.common.collect.Maps;

public class CommandManager {

	private static Map<String, Command> commands;

	public static void load() {
		commands = Maps.newHashMap();
		commands.put("about", new AboutCommand());
		commands.put("sit", new SitCommand());
		commands.put("help", new HelpCommand());
		commands.put("reloadplugins", new ReloadPlugins());
	}

	public static boolean hasCommand(String message) {

		if (message.startsWith(":") && message.length() > 1) {

			String commandName = message.split(":")[1];

			if (commands.containsKey(commandName)) {
				return true;
			}
		}

		return false;
	}

	public static void invokeCommand(Player player, String message) {

		String commandName = message.split(":")[1];

		if (commands.containsKey(commandName)) {
			commands.get(commandName).handleCommand(player, message);
		}
	}
}