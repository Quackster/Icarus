package org.alexdev.icarus.game.commands;

import org.alexdev.icarus.game.commands.types.*;
import org.alexdev.icarus.game.commands.types.info.AboutCommand;
import org.alexdev.icarus.game.commands.types.info.HelpCommand;
import org.alexdev.icarus.game.commands.types.reload.ReloadCatalogueCommand;
import org.alexdev.icarus.game.commands.types.reload.ReloadItemDefinitions;
import org.alexdev.icarus.game.commands.types.reload.ReloadPluginsCommand;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.util.config.Configuration;
import org.alexdev.icarus.util.locale.Locale;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class CommandManager {

    private Map<String[], Command> commands;

    private static final Logger log = LoggerFactory.getLogger(CommandManager.class);
    private static CommandManager instance;

    public CommandManager() {
        commands = new HashMap<>();
        commands.put(new String[] { "effect" }, new EffectCommand());
        commands.put(new String[] { "about", "info" }, new AboutCommand());
        commands.put(new String[] { "sit" }, new SitCommand());
        commands.put(new String[] { "help", "commands" }, new HelpCommand());
        commands.put(new String[] { "debugfurni" }, new DebugFurnitureCommand());
        commands.put(new String[] { "rollerspeed" }, new RollerSpeedCommand());
        commands.put(new String[] { "reloadconfig" }, new ReloadConfigCommand());
        commands.put(new String[] { "resetdecor" }, new ResetDecorationCommand());
        commands.put(new String[] { "clearinventory" }, new ClearInventoryCommand());
        commands.put(new String[] { "regencollision" }, new RegenCollisionCommand());
        commands.put(new String[] { "reloadplugins" }, new ReloadPluginsCommand());
        commands.put(new String[] { "reloadfurni" }, new ReloadItemDefinitions());
        commands.put(new String[] { "reloadcatalog" }, new ReloadCatalogueCommand());
        commands.put(new String[] { "moonwalk", "mj" }, new MoonWalkCommand());
        commands.put(new String[] { "diagonal", "diag" }, new WalkDiagonalCommand());

        if (Configuration.getInstance().getServerConfig().get("Logging", "log.items.loaded", Boolean.class)) {
            log.info("Loaded {} commands", commands.size());
        }
    }

    /**
     * Gets the command.
     *
     * @param commandName the command name
     * @return the command
     */
    private Command getCommand(String commandName) {

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
    public boolean hasCommand(Player player, String message) {

        if (message.startsWith(":") && message.length() > 1) {

            String commandName = message.split(":")[1].split(" ")[0];
            Command cmd = getCommand(commandName);

            if (cmd != null) {
                return this.hasCommandPermission(player, cmd);
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
    public boolean hasCommandPermission(Player player, Command cmd) {

        if (cmd.getPermissions().length > 0) {
            for (String permission : cmd.getPermissions()) {                   
                if (player.getDetails().hasPermission(permission)) {    
                    return true;
                }
            }
        } else {
            return true;
        }

        return false;
    }

    /**
     * Invoke command.
     *
     * @param player the player
     * @param message the message
     */
    public void invokeCommand(Player player, String message) {

        String commandName = message.split(":")[1].split(" ")[0];
        Command cmd = getCommand(commandName);

        String[] args = new String[0];

        if (message.length() > (commandName.length() + 2)) {
            args = message.replace(":" + commandName + " ", "").split(" ");
        }

        if (cmd != null) {
            
            if (args.length < cmd.getArguments().length) {
                player.sendMessage(Locale.getInstance().getEntry("player.commands.no.args"));
                return;
            }
            
            cmd.handleCommand(player, message, args);
        }
    }

    /**
     * Gets the commands.
     *
     * @return the commands
     */
    public Map<String[], Command> getCommands() {
        return commands;
    }

    /**
     * Gets the instance
     *
     * @return the instance
     */
    public static CommandManager getInstance() {

        if (instance == null) {
            instance = new CommandManager();
        }

        return instance;
    }
}