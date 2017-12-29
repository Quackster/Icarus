package org.alexdev.icarus.game.room.tasks;

import org.alexdev.icarus.game.pathfinder.Position;
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
            boolean sendToDoor = false;

            if (entity.getRoomUser().getTile() == null) {
                sendToDoor = true;
            }

            if (!this.room.getMapping().isTileWalkable(entity.getX(),entity.getY(), entity)) {
                sendToDoor = true;
            }

            if (sendToDoor) {
                Position doorLocation = this.room.getModel().getDoorLocation();
                entity.getRoomUser().warpTo(doorLocation.getX(), doorLocation.getY(), doorLocation.getRotation());
            }

            RoomTile tile = this.room.getMapping().getReachableTile(entity);

            if (tile == null) {
                continue;
            }

            RoomUser roomUser = entity.getRoomUser();
            roomUser.walkTo(tile.getX(), tile.getY());
        }

        this.room.getScheduler().removeTask(PetTask.class);
        this.room.getScheduler().scheduleEvent(RandomInteger.getRandom(10, 30), TimeUnit.SECONDS, TaskType.REPEAT, new PetTask(room));
    }
}
