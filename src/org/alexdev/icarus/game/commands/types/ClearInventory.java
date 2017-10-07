package org.alexdev.icarus.game.commands.types;

import java.util.ArrayList;
import java.util.List;

import org.alexdev.icarus.game.commands.Command;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.player.Player;

public class ClearInventory extends Command {

    @Override
    public void addPermissions() {
        this.permissions.add("user");
    }

    @Override
    public void handleCommand(Player player, String message, String[] args) {
        
        List<Item> itemsToDelete = new ArrayList<>();
        
        for (Item item : player.getInventory().getItems().values()) {
            item.delete();
            itemsToDelete.add(item);
        }
        
        for (Item item : itemsToDelete) {
            player.getInventory().remove(item);
        }
        
        player.sendMessage("You have cleared your inventory!");
    }

    @Override
    public String getDescription() {
        return "Clears the entire inventory of a user, including pets, but not badges";
    }

}
