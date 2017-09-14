package org.alexdev.icarus.game.commands.types;

import org.alexdev.icarus.game.commands.Command;
import org.alexdev.icarus.game.player.Player;

public class HelpCommand extends Command {

    @Override
    public void handleCommand(Player player, String message) {
        
        StringBuilder about = new StringBuilder();
        about.append("Commands:\n\n");
        about.append("- :sit\n");
        about.append("- :about\n");
        about.append("- :help.");
        
        //TODO: List commands here programmically.
        
        player.sendMessage(about.toString());
    }

    @Override
    public void addPermissions() {
        this.permissions.add("user");
    }
}
