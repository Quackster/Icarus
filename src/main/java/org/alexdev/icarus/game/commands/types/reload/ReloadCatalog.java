package org.alexdev.icarus.game.commands.types.reload;

import org.alexdev.icarus.game.catalogue.CatalogueManager;
import org.alexdev.icarus.game.commands.Command;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.room.user.ChatType;

public class ReloadCatalog extends Command {
    
    @Override
    public void addPermissions() {
        this.permissions.add("administrator");
    }
    
    @Override
    public void handleCommand(Player player, String message, String[] args) {
        CatalogueManager.load();
        player.getRoomUser().chatSelf(ChatType.WHISPER, "Catalogue pages and items have been reloaded.");
    }


    @Override
    public String getDescription() {
        return "Reloads catalogue items and pages.";
    }
}
