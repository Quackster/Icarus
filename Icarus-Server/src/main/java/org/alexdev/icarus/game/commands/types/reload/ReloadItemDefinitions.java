package org.alexdev.icarus.game.commands.types.reload;

import org.alexdev.icarus.game.commands.Command;
import org.alexdev.icarus.game.entity.Entity;
import org.alexdev.icarus.game.item.ItemManager;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.user.ChatType;

public class ReloadItemDefinitions extends Command {

    @Override
    public void addPermissions() {
        this.permissions.add("operator");
        this.permissions.add("administrator");
    }

    @Override
    public void handleCommand(Entity entity, String message, String[] args) {

        if (entity instanceof Player) {
            Player player = (Player) entity;

            ItemManager.getInstance().reload();
            player.getRoomUser().chatSelf(ChatType.WHISPER, "Furniture definitions have been reloaded!");
        } else {
            System.out.println("Furniture definitions have been reloaded!");
        }
    }

    @Override
    public String getDescription() {
        return "Reload all furniture definitions.";
    }

}
