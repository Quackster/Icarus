package org.alexdev.icarus.game.commands.types;

import org.alexdev.icarus.game.commands.Command;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.user.ChatType;

public class DebugFurnitureCommand extends Command {
    
    @Override
    public void addPermissions() {
        this.permissions.add("administrator");
    }
        
    @Override
    public void handleCommand(Player player, String message, String[] args) {
        
        if (!player.getMetadata().hasMetadata("debugfurniture")) {
            player.getMetadata().set("debugfurniture", false);
        }
        
        player.getMetadata().set("debugfurniture", !player.getMetadata().getBoolean("debugfurniture"));
        player.getRoomUser().chatSelf(ChatType.WHISPER, "I have turned " + (player.getMetadata().getBoolean("debugfurniture") ? "on" : "off") + " the debug furniture command.");
    }


    @Override
    public String getDescription() {
        return "Allow finding the ID's of items easier.";
    }
}
