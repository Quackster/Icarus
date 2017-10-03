package org.alexdev.icarus.game.commands.types;

import org.alexdev.icarus.game.commands.Command;
import org.alexdev.icarus.game.player.Player;

public class AboutCommand extends Command {

    @Override
    public void handleCommand(Player player, String message, String[] args) {
        
        StringBuilder about = new StringBuilder();
        about.append("Icarus server written by Quackster, powered by Java!\n\n");
        about.append("<b>With the help of:</b>\n\n");
        about.append("- Leon\n");
        about.append("- Sledmore\n");
        about.append("- Spreedblood\n");
        about.append("- Glaceon\n");
        about.append("- Nillus\n");
        
        player.sendCustomMessage("Powered by Icarus Server", about.toString(), "icarus", "Development Thread", "http://");
    }

    @Override
    public void addPermissions() {
        this.permissions.add("user");
    }
}
