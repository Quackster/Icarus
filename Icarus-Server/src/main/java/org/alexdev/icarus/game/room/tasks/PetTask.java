package org.alexdev.icarus.game.room.tasks;

import org.alexdev.icarus.game.pets.Pet;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.model.RoomTile;
import org.alexdev.icarus.game.room.scheduler.RoomTask;
import org.alexdev.icarus.game.room.scheduler.TaskType;
import org.alexdev.icarus.game.room.user.RoomUser;
import org.alexdev.icarus.util.RandomInteger;

import java.util.List;
import java.util.concurrent.TimeUnit;

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

        room.getScheduler().removeTask(PetTask.class);
        room.getScheduler().scheduleEvent(RandomInteger.getRandom(10, 30), TimeUnit.SECONDS, TaskType.REPEAT, new PetTask(room));
    }
}
