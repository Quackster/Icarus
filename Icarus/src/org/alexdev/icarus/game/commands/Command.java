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

    public abstract void addPermissions();
    public abstract void handleCommand(Player player, String message);

    public List<String> getPermissions() {
        return permissions;
    }
}
