package org.alexdev.icarus.game.commands.types;

import java.util.Map.Entry;

import org.alexdev.icarus.game.commands.Command;
import org.alexdev.icarus.game.commands.CommandManager;
import org.alexdev.icarus.game.player.Player;

public class HelpCommand extends Command {

    @Override
    public void handleCommand(Player player, String message, String[] args) {
        
        StringBuilder about = new StringBuilder();
        about.append("Commands:\n\n");
        
        for (Entry<String[], Command> set : CommandManager.getCommands().entrySet()) {
            
            if (!CommandManager.hasCommandPermission(player, set.getValue())) {
                continue;
            }
            
            about.append(":" + String.join("/", set.getKey()) + " - description.\n");
        }

        player.sendNotification(about.toString());
    }

    @Override
    public void addPermissions() {
        this.permissions.add("user");
    }
}
