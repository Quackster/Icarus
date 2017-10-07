package org.alexdev.icarus.game.commands.types;

import org.alexdev.icarus.game.commands.Command;
import org.alexdev.icarus.game.item.ItemManager;
import org.alexdev.icarus.game.player.Player;

public class ReloadFurni extends Command {

    @Override
    public void addPermissions() {
        this.permissions.add("administrator");
    }

    @Override
    public void handleCommand(Player player, String message, String[] args) {
        ItemManager.load();
        player.sendMessage("Furniture definitions have been reloaded!");
    }

    @Override
    public String getDescription() {
        return "Reload all furniture definitions";
    }

}
