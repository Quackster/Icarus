package org.alexdev.icarus.game.commands.types;

import java.util.ArrayList;
import java.util.List;

import org.alexdev.icarus.game.commands.Command;
import org.alexdev.icarus.game.entity.Entity;
import org.alexdev.icarus.game.item.Item;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.user.ChatType;

public class ClearInventoryCommand extends Command {

    @Override
    public void addPermissions() {
        this.permissions.add("user");
    }

    @Override
    public void handleCommand(Entity entity, String message, String[] args) {

        if (entity instanceof Player) {
            Player player = (Player)entity;

            List<Item> itemsToDelete = new ArrayList<>();

            for (Item item : player.getInventory().getItems().values()) {
                item.delete();
                itemsToDelete.add(item);
            }

            for (Item item : itemsToDelete) {
                player.getInventory().remove(item);
            }

            player.getRoomUser().chatSelf(ChatType.WHISPER, "I have cleared my inventory!");

        } else {
            System.out.println("This command can only be performed by a player");
        }
    }

    @Override
    public String getDescription() {
        return "Clears the entire inventory of a user, including pets, but not badges";
    }

}
