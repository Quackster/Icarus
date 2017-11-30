package org.alexdev.icarus.game.commands.types.info;

import org.alexdev.icarus.Icarus;
import org.alexdev.icarus.game.commands.Command;
import org.alexdev.icarus.game.entity.Entity;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.player.PlayerManager;
import org.alexdev.icarus.util.Util;

public class AboutCommand extends Command {
    
    @Override
    public void addPermissions() {
        this.permissions.add("operator");
        this.permissions.add("user");
    }
    
    @Override
    public void handleCommand(Entity entity, String message, String[] args) {
        StringBuilder about = new StringBuilder();
        about.append("Icarus server written by Quackster, powered by Java!\n");
        about.append("\n<b>Server Status</b>\n\n");
        about.append("Users Online: " + PlayerManager.getInstance().getPlayers().size() + "\n");
        about.append("Uptime: " + Util.getReadableTimestamp(Icarus.getStartupTime()) + "\n");
        about.append("\n<b>Contributors</b>\n\n");
        about.append("- Sledmore\n");
        about.append("- Leon\n");
        about.append("- Spreedblood\n");
        about.append("- Glaceon\n");
        about.append("- Jaxter\n");
        about.append("- Nillus\n");

        if (entity instanceof Player) {
            Player player = (Player) entity;
            player.sendCustomMessage("Powered by Icarus Server", about.toString(), "icarus", "Development Thread", "http://");
        } else {
            System.out.println(about.toString());
        }
    }

    @Override
    public String getDescription() {
        return "Gets the information about this server.";
    }
}
