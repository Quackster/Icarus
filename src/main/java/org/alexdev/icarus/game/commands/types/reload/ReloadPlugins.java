package org.alexdev.icarus.game.commands.types.reload;

import org.alexdev.icarus.game.commands.Command;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.plugins.PluginManager;

public class ReloadPlugins extends Command {
    
    @Override
    public void addPermissions() {
        this.permissions.add("administrator");
    }
    
    @Override
    public void handleCommand(Player player, String message, String[] args) {
        PluginManager.disposePlugins();
        PluginManager.load();
    }


    @Override
    public String getDescription() {
        return "Reloads plugins, may cause undefined errors!";
    }
}
