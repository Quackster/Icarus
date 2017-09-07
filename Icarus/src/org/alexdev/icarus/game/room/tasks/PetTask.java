package org.alexdev.icarus.game.room.tasks;

import java.util.List;

import org.alexdev.icarus.game.entity.Entity;
import org.alexdev.icarus.game.entity.EntityType;
import org.alexdev.icarus.game.pathfinder.Position;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.RoomUser;

public class PetTask extends RoomTask {

    private Room room;

    public PetTask(Room room) {
        this.room = room;
    }

    public void execute() {

        try {

            if (this.canTick(5)) {
                
                if (room.getEntities(EntityType.PET).size() == 0) {
                    return;
                }

                List<Entity> entities = room.getEntities(EntityType.PET);

                for (int i = 0; i < entities.size(); i++) {

                    Entity entity = entities.get(i);

                    Position tile = this.room.getMapping().getRandomWalkableTile();

                    RoomUser roomUser = entity.getRoomUser();
                    roomUser.walkTo(tile.getX(), tile.getY());

                }
            }

        } catch (Exception e) { e.printStackTrace(); }

        this.tick();
    }
}
