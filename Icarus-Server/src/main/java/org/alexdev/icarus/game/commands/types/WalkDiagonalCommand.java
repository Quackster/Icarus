package org.alexdev.icarus.game.commands.types;

import org.alexdev.icarus.game.commands.Command;
import org.alexdev.icarus.game.entity.Entity;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.user.ChatType;

public class WalkDiagonalCommand extends Command {

    @Override
    public void addPermissions() {
        this.permissions.add("user");
    }

    @Override
    public void handleCommand(Entity entity, String message, String[] args) {

        if (entity instanceof Player) {

            Player player = (Player) entity;

            if (player.getRoom() == null) {
                return;
            }

            if (!player.getRoom().hasOwnership(player.getEntityId())) {
                return;
            }

            Room room = player.getRoom();
            String key = "disableWalkDiagonal";

            if (!room.getMetadata().hasMetadata(key)) {
                room.getMetadata().set(key, false);
            }

            room.getMetadata().set(key, !room.getMetadata().getBoolean(key));
            player.getRoomUser().chatSelf(ChatType.WHISPER, "I have turned " + (room.getMetadata().getBoolean(key) ? "off" : "on") + " diagonal room walking.");

        } else {
            System.out.println("This command can only be performed by a player");
        }
    }

    @Override
    public String getDescription() {
        return "Turns on/off whether users can walk diagonal in this room. Used by room owner only.";
    }
}
