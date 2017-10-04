package org.alexdev.icarus.game.commands.types;

import org.alexdev.icarus.game.commands.Command;
import org.alexdev.icarus.game.player.Player;

public class EffectCommand extends Command {

    @Override
    public void handleCommand(Player player, String message, String[] args) {
        
        /*player.getRoom().getMetadata().set("enableEffectCommand", true);
        player.getRoom().saveMetadata();*/

    }

    @Override
    public String getDescription() {
        return "Enable an effect ID without using an existing inventory effect.";
    }

    @Override
    public void addPermissions() {
        this.permissions.add("user");
    }

}
