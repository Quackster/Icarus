package org.alexdev.icarus.game.commands;

import java.util.Map;
import java.util.Map.Entry;

import org.alexdev.icarus.game.commands.types.*;
import org.alexdev.icarus.game.player.Player;

import com.google.common.collect.Maps;

public class CommandManager {

    private static Map<String[], Command> commands;

    public static void load() {
        commands = Maps.newHashMap();
        commands.put(new String[] { "about", "info" }, new AboutCommand());
        commands.put(new String[] { "sit" }, new SitCommand());
        commands.put(new String[] { "help" }, new HelpCommand());
        commands.put(new String[] { "reloadplugins" }, new ReloadPlugins());
    }

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
    
    public static boolean hasCommand(Player player, String message) {

        if (message.startsWith(":") && message.length() > 1) {

            String commandName = message.split(":")[1];
            Command cmd = getCommand(commandName);
            
            if (cmd != null) {

                for (String permission : cmd.getPermissions()) {                   
                    if (player.hasPermission(permission)) {    
                        return true;
                    }
                }
            }
        }

        return false;
    }

    public static void invokeCommand(Player player, String message) {

        String commandName = message.split(":")[1];

        Command cmd = getCommand(commandName);
        
        if (cmd != null) {
            cmd.handleCommand(player, message);
        }
    }
}