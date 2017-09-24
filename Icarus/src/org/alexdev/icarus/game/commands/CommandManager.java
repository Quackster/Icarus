package org.alexdev.icarus.game.commands;

import java.util.Map;
import java.util.Map.Entry;

import org.alexdev.icarus.game.commands.types.*;
import org.alexdev.icarus.game.player.Player;

import com.google.common.collect.Maps;

public class CommandManager {

    private static Map<String[], Command> commands;

    /**
     * Load.
     */
    public static void load() {
        commands = Maps.newHashMap();
        commands.put(new String[] { "about", "info" }, new AboutCommand());
        commands.put(new String[] { "sit" }, new SitCommand());
        commands.put(new String[] { "help", "commands" }, new HelpCommand());
        commands.put(new String[] { "reloadplugins" }, new ReloadPlugins());
        commands.put(new String[] { "debugfurniture" }, new DebugFurniture());
    }

    /**
     * Gets the command.
     *
     * @param commandName the command name
     * @return the command
     */
    private static Command getCommand(String commandName) {
        
        for (Entry<String[], Command> entrySet : commands.entrySet()) {
            for (String name : entrySet.getKey()) {
                
                if (commandName.equalsIgnoreCase(name)) {
                    return entrySet.getValue();
                }
            }
        }
        
        return null;
    }
    
    /**
     * Checks for command.
     *
     * @param player the player
     * @param message the message
     * @return true, if successful
     */
    public static boolean hasCommand(Player player, String message) {

        if (message.startsWith(":") && message.length() > 1) {

            String commandName = message.split(":")[1];
            Command cmd = getCommand(commandName);
            
            if (cmd != null) {
                return CommandManager.hasCommandPermission(player, cmd);
            }
        }

        return false;
    }
    
    /**
     * Checks for command permission.
     *
     * @param player the player
     * @param cmd the command
     * @return true, if successful
     */
    public static boolean hasCommandPermission(Player player, Command cmd) {
        for (String permission : cmd.getPermissions()) {                   
            if (player.getDetails().hasPermission(permission)) {    
                return true;
            }
        }
        
        return false;
    }

    /**
     * Invoke command.
     *
     * @param player the player
     * @param message the message
     */
    public static void invokeCommand(Player player, String message) {

        String commandName = message.split(":")[1];
        Command cmd = getCommand(commandName);
        
        if (cmd != null) {
            cmd.handleCommand(player, message);
        }
    }

    /**
     * Gets the commands.
     *
     * @return the commands
     */
    public static Map<String[], Command> getCommands() {
        return commands;
    }
}