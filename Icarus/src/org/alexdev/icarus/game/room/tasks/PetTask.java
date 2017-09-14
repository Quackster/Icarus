package org.alexdev.icarus.game.room.tasks;

import java.util.List;

import org.alexdev.icarus.game.entity.Entity;
import org.alexdev.icarus.game.pathfinder.Position;
import org.alexdev.icarus.game.pets.Pet;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.user.RoomUser;

public class PetTask extends RoomTask {

    private Room room;

    public PetTask(Room room) {
        this.room = room;
    }

    public void execute() {

        try {

            if (this.canTick(5)) {

                List<Pet> pets = this.room.getEntityManager().getEntitiesByClass(Pet.class);

                if (pets.size() == 0) {
                    return;
                }

                for (int i = 0; i < pets.size(); i++) {

                    Entity entity = pets.get(i);
                    Position tile = this.room.getMapping().getAvaliableTile();

                    RoomUser roomUser = entity.getRoomUser();
                    roomUser.walkTo(tile.getX(), tile.getY());

                }
            }

        } catch (Exception e) {
            e.printStackTrace(); 
        }

        this.tick();
    }
}
