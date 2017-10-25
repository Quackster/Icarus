package org.alexdev.icarus.game.commands.types;

import org.alexdev.icarus.game.commands.Command;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.user.ChatType;

public class MoonWalkCommand extends Command {

    @Override
    public void addPermissions() {
        this.permissions.add("user");
    }

    @Override
    public void handleCommand(Player player, String message, String[] args) {

        if (!player.getMetadata().hasMetadata("moonwalk")) {
            player.getMetadata().set("moonwalk", false);
        }

        player.getMetadata().set("moonwalk", !player.getMetadata().getBoolean("moonwalk"));
        player.getRoomUser().chatSelf(ChatType.WHISPER, "I have turned " + (player.getMetadata().getBoolean("moonwalk") ? "on" : "off") + " moonwalking.");
    }

    @Override
    public String getDescription() {
        return "Reverses the user's rotation when walking to appear as if moonwalking.";
    }
}
