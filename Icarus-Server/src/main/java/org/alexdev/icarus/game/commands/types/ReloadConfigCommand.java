package org.alexdev.icarus.game.commands.types;

import org.alexdev.icarus.game.GameSettings;
import org.alexdev.icarus.game.commands.Command;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.user.ChatType;

public class ReloadConfigCommand extends Command {

    @Override
    public void addPermissions() {
        this.permissions.add("administrator");
    }

    @Override
    public void handleCommand(Player player, String message, String[] args) {
        GameSettings.getInstance().reload();
        player.getRoomUser().chatSelf(ChatType.WHISPER, "The configuration values have been reloaded.");
    }

    @Override
    public String getDescription() {
        return "Reload configuration from the table 'site_config'";
    }
}
