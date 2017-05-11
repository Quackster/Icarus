package org.alexdev.icarus.game.room;

import org.alexdev.icarus.game.pathfinder.Position;

public class RoomMapping {

    private Room room;

    public RoomMapping(Room room) {
        this.room = room;
    }

    public boolean isValidStep(Position position, Position tmp, boolean isFinalMove) {
        
        if (this.room.getModel().invalidXYCoords(position.getX(), position.getY())) {
            return false;
        }
        
        if (this.room.getModel().invalidXYCoords(tmp.getX(), tmp.getY())) {
            return false;
        }
        
        return true;
    }

}
