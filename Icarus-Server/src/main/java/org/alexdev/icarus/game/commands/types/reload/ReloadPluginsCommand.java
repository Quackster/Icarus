package org.alexdev.icarus.game.commands.types.reload;

import org.alexdev.icarus.game.commands.Command;
import org.alexdev.icarus.game.entity.Entity;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.plugins.PluginManager;

public class ReloadPluginsCommand extends Command {
    
    @Override
    public void addPermissions() {
        this.permissions.add("operator");
        this.permissions.add("administrator");
    }
    
    @Override
    public void handleCommand(Entity entity, String message, String[] args) {
        PluginManager.getInstance().disposePlugins();
        PluginManager.getInstance().reload();
    }


    @Override
    public String getDescription() {
        return "Reloads plugins, may cause undefined errors!";
    }
}
