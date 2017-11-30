package org.alexdev.icarus.game.commands.types;

import org.alexdev.icarus.game.commands.Command;
import org.alexdev.icarus.game.entity.Entity;
import org.alexdev.icarus.game.entity.EntityStatus;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.user.RoomUser;

public class SitCommand extends Command {

    @Override
    public void addPermissions() {
        this.permissions.add("user");
    }
    
    @Override
    public void handleCommand(Entity entity, String message, String[] args) {
        if (entity instanceof Player) {
            Player player = (Player) entity;
            RoomUser roomUser = player.getRoomUser();

            if (roomUser.getRoom() != null) {

                if (roomUser.isWalking()) {
                    return;
                }

                if (roomUser.containsStatus(EntityStatus.SIT)) {
                    return;
                }

                int rotation = roomUser.getPosition().getRotation();

                if (rotation != 0 && rotation != 2 && rotation != 4 && rotation != 6) {
                    return;
                }

                roomUser.removeStatus(EntityStatus.DANCE);

                roomUser.setStatus(EntityStatus.SIT, "0.5");
                roomUser.setNeedUpdate(true);
            }
        }
    }

    @Override
    public String getDescription() {
        return "Sit anywhere.";
    }
}
