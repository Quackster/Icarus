package org.alexdev.icarus.game.commands.types;

import org.alexdev.icarus.game.commands.Command;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.chat.ChatType;

public class DebugFurniture extends Command {

    @Override
    public void handleCommand(Player player, String message) {
        
        if (!player.getMetadata().hasMetadata("debugfurniture")) {
            player.getMetadata().set("debugfurniture", false);
        }
        
        player.getMetadata().set("debugfurniture", !player.getMetadata().getAsBool("debugfurniture"));
        player.getRoomUser().chatSelf(ChatType.WHISPER, "I have turned " + (player.getMetadata().getAsBool("debugfurniture") ? "on" : "off") + " the debug furniture command.");
    }

    @Override
    public void addPermissions() {
        this.permissions.add("administrator");
    }
}
