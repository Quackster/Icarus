package org.alexdev.icarus.game.commands.types.reload;

import org.alexdev.icarus.game.GameSettings;
import org.alexdev.icarus.game.commands.Command;
import org.alexdev.icarus.game.entity.Entity;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.user.ChatType;

public class ReloadConfigCommand extends Command {

    @Override
    public void addPermissions() {
        this.permissions.add("operator");
        this.permissions.add("administrator");
    }

    @Override
    public void handleCommand(Entity entity, String message, String[] args) {
        GameSettings.getInstance().reload();

        if (entity instanceof Player) {
            Player player = (Player) entity;
            player.getRoomUser().chatSelf(ChatType.WHISPER, "The configuration values have been reloaded.");
        } else {
            System.out.println("The configuration values have been reloaded.");
        }
    }

    @Override
    public String getDescription() {
        return "Reload configuration from the table 'site_config'";
    }
}
