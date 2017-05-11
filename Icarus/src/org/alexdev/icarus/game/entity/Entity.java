package org.alexdev.icarus.game.entity;

import org.alexdev.icarus.game.player.PlayerDetails;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.RoomUser;

public abstract class Entity {

    public abstract PlayerDetails getDetails();
    public abstract RoomUser getRoomUser();
    public abstract EntityType getType();

    public Room getRoom() {
        return getRoomUser().getRoom();
    }

    public boolean inRoom() {
        return getRoomUser().getRoom() != null;
    }

}
