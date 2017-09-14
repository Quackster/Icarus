package org.alexdev.icarus.game.room.tasks;

import java.util.List;

import org.alexdev.icarus.game.entity.Entity;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.scheduler.ScheduledTask;
import org.alexdev.icarus.game.room.user.RoomUser;

public class CarryItemTask implements ScheduledTask {

    private Room room;

    public CarryItemTask(Room room) {
        this.room = room;
    }

    public void execute() {

        if (this.room.getEntityManager().getEntities().size() == 0) {
            return;
        }

        List<Entity> entities = this.room.getEntityManager().getEntities();

        for (int i = 0; i < entities.size(); i++) {

            Entity entity = entities.get(i);

            RoomUser roomUser = entity.getRoomUser();

            if (roomUser.getCarryTimer() > 0) {
                roomUser.setCarryTimer(roomUser.getCarryTimer() - 1);
            } else {
                if (roomUser.getCarryTimer() == 0) {
                    roomUser.carryItem(0);
                    roomUser.setCarryTimer(-1);
                }
            }

        }
    }
}
