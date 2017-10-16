package org.alexdev.icarus.game.room.tasks;

import java.util.List;

import org.alexdev.icarus.game.pets.Pet;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.scheduler.RoomTask;

public class PetTask extends RoomTask {

    private Room room;

    public PetTask(Room room) {
        this.room = room;
    }

    @Override
    public void execute() {

        List<Pet> pets = this.room.getEntityManager().getEntitiesByClass(Pet.class);

        if (pets.isEmpty()) {
            return;
        }

        for (int i = 0; i < pets.size(); i++) {

            /*Entity entity = pets.get(i);
                    Position tile = this.room.getMapping().getAvaliableTile();

                    RoomUser roomUser = entity.getRoomUser();
                    roomUser.walkTo(tile.getX(), tile.getY());*/


        }

        pets.clear();
        pets = null;
    }
}
