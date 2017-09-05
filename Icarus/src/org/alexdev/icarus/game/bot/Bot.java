package org.alexdev.icarus.game.bot;

import org.alexdev.icarus.game.entity.EntityType;
import org.alexdev.icarus.game.entity.Entity;
import org.alexdev.icarus.game.player.PlayerDetails;
import org.alexdev.icarus.game.room.RoomUser;

public class Bot extends Entity {

    private PlayerDetails details;
    private RoomUser roomUser;

    public Bot() {
        this.details = new PlayerDetails(this);
        this.roomUser = new RoomUser(this);
    }
    
    @Override
    public EntityType getType() {
        return EntityType.BOT;
    }

    @Override
    public PlayerDetails getDetails() {
        return this.details;
    }

    @Override
    public RoomUser getRoomUser() {
        return this.roomUser;
    }

    @Override
    public void dispose() {
        this.disposed = true;
    }

}
