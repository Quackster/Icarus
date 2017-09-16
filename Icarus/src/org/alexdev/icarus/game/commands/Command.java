package org.alexdev.icarus.game.commands;

import java.util.List;

import org.alexdev.icarus.game.player.Player;

import com.google.common.collect.Lists;

public abstract class Command {
   
    protected List<String> permissions;
    
    public Command() {
        this.permissions = Lists.newArrayList();
        this.addPermissions();
    }

    /**
     * Adds the permissions.
     */
    public abstract void addPermissions();
    
    /**
     * Handle command.
     *
     * @param player the player
     * @param message the message
     */
    public abstract void handleCommand(Player player, String message);

    /**
     * Gets the permissions.
     *
     * @return the permissions
     */
    public List<String> getPermissions() {
        return permissions;
    }
}
