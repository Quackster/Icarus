package org.alexdev.icarus.game.commands.types;

import org.alexdev.icarus.game.commands.Command;
import org.alexdev.icarus.game.player.Player;

public class AboutCommand extends Command {

    @Override
    public void handleCommand(Player player, String message, String[] args) {
        
        StringBuilder about = new StringBuilder();
        about.append("Icarus server written by Quackster, powered by Java among other things!\n\n");
        about.append("With the help of:\n\n");
        about.append("- Leon\n");
        about.append("- Spreedblood\n");
        about.append("- Glaceon\n");
        player.sendMessage(about.toString());
    }

    @Override
    public void addPermissions() {
        this.permissions.add("user");
    }
}
