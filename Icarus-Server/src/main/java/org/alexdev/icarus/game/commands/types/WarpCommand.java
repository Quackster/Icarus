package org.alexdev.icarus.game.commands.types;

import org.alexdev.icarus.game.commands.Command;
import org.alexdev.icarus.game.entity.Entity;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.user.ChatType;
import org.alexdev.icarus.game.room.user.RoomUser;

public class WarpCommand extends Command {

    public static final String COMMAND_METADATA = "teleporting";
    
    @Override
    public void addPermissions() {
        this.permissions.add("administrator");
    }
    
    @Override
    public void handleCommand(Entity entity, String message, String[] args) {
        if (entity instanceof Player) {
            Player player = (Player) entity;
            RoomUser roomUser = player.getRoomUser();

            if (roomUser.getRoom() != null) {
                if (!player.getMetadata().hasMetadata(COMMAND_METADATA)) {
                    player.getMetadata().set(COMMAND_METADATA, false);
                }

                player.getMetadata().set(COMMAND_METADATA, !player.getMetadata().getBoolean(COMMAND_METADATA));
                player.getRoomUser().chatSelf(ChatType.WHISPER, "I have turned " + (player.getMetadata().getBoolean(COMMAND_METADATA) ? "on" : "off") + " teleporting.");
            }
        }
    }

    @Override
    public String getDescription() {
        return "Click and teleport anywhere.";
    }
}
