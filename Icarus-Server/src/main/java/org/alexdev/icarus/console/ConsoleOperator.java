package org.alexdev.icarus.console;

import org.alexdev.icarus.game.entity.Entity;
import org.alexdev.icarus.game.entity.EntityType;
import org.alexdev.icarus.game.player.PlayerDetails;
import org.alexdev.icarus.game.room.user.RoomUser;

public class ConsoleOperator extends Entity {

    @Override
    public boolean hasPermission(String permission) {
        return permission.equalsIgnoreCase("operator");
    }

    @Override
    public PlayerDetails getDetails() {
        return null;
    }

    @Override
    public RoomUser getRoomUser() {
        return null;
    }

    @Override
    public EntityType getType() {
        return null;
    }

    @Override
    public void dispose() {

    }
}
