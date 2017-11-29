package org.alexdev.icarus.game.commands;

import java.util.ArrayList;
import java.util.List;

import org.alexdev.icarus.game.entity.Entity;
import org.alexdev.icarus.game.player.Player;

public abstract class Command {
   
    protected List<String> permissions;
    protected List<String> arguments;
    
    public Command() {
        this.permissions = new ArrayList<>();
        this.arguments = new ArrayList<>();
        this.addPermissions();
        this.addArguments();
    }

    /**
     * Adds the permissions.
     */
    public abstract void addPermissions();
    
    /**
     * Adds the argument names, must be overridden
     */
    public void addArguments() { };
    
    /**
     * Handle command.
     *
     * @param entity the entity
     * @param message the message
     */
    public abstract void handleCommand(Entity entity, String message, String[] args);
    
    /**
     * Gets the description.
     *
     * @return the description
     */
    public abstract String getDescription();

    /**
     * Gets the permissions.
     *
     * @return the permissions
     */
    public String[] getPermissions() {
        return this.permissions.parallelStream().toArray(String[]::new);
    }

    /**
     * Gets the arguments.
     *
     * @return the arguments
     */
    public String[] getArguments() {
        return this.arguments.parallelStream().toArray(String[]::new);
    }
}
