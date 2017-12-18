package org.alexdev.icarus.game.room.tasks;

import org.alexdev.icarus.game.pets.Pet;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.model.RoomTile;
import org.alexdev.icarus.game.room.scheduler.RoomTask;
import org.alexdev.icarus.game.room.user.RoomUser;

import java.util.List;

public class PetTask extends RoomTask {

    private Room room;

    public PetTask(Room room) {
        this.room = room;
    }

    @Override
    public void execute() {
        List<Pet> pets = this.room.getEntityManager().getEntitiesByClass(Pet.class);

        for (Pet entity : pets) {
            RoomTile tile = this.room.getMapping().getReachableTile(entity);
            RoomUser roomUser = entity.getRoomUser();
            roomUser.walkTo(tile.getX(), tile.getY());
        }
    }
}
