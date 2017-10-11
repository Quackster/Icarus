package org.alexdev.icarus.game.commands.types;

import java.util.Map.Entry;

import org.alexdev.icarus.game.commands.Command;
import org.alexdev.icarus.game.commands.CommandManager;
import org.alexdev.icarus.game.player.Player;

public class HelpCommand extends Command {

    @Override
    public void addPermissions() {
        this.permissions.add("user");
    }
    
    @Override
    public void handleCommand(Player player, String message, String[] args) {

        StringBuilder about = new StringBuilder();
        about.append("Commands:\n\n");

        for (Entry<String[], Command> set : CommandManager.getCommands().entrySet()) {

            if (!CommandManager.hasCommandPermission(player, set.getValue())) {
                continue;
            }

            Command command = set.getValue();

            about.append(":" + String.join("/", set.getKey()));

            if (command.getArguments().length > 0) {
                if (command.getArguments().length > 1) {
                    about.append(" - [" + String.join("] [", command.getArguments()) + "]");
                } else {
                    about.append(" - [" + command.getArguments()[0] + "]");                    
                }
            }

            about.append(" - " + command.getDescription() + "\n");
        }

        player.sendMOTD(about.toString());
    }
    

    @Override
    public String getDescription() {
        return "Gets the list of available help commands by permission.";
    }
}
