package org.alexdev.icarus.game.commands.types;

import org.alexdev.icarus.game.commands.Command;
import org.alexdev.icarus.game.entity.Entity;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.user.ChatType;

public class RegenCollisionCommand extends Command {

    @Override
    public void addPermissions() {
        this.permissions.add("administrator");
    }

    @Override
    public void handleCommand(Entity entity, String message, String[] args) {
        if (entity instanceof Player) {
            Player player = (Player) entity;

            if (player.getRoom() == null) {
                return;
            }

            player.getRoomUser().getRoom().getMapping().regenerateCollisionMaps(false);
            player.getRoomUser().chatSelf(ChatType.WHISPER, "The collision map has been regenerated.");
        }
    }

    @Override
    public String getDescription() {
        return "Forces a regeneration of the furniture collision map, suitable after furni definitions reload.";
    }

}
