package org.alexdev.icarus.game.room.tasks;

import java.util.List;

import org.alexdev.icarus.game.entity.Entity;
import org.alexdev.icarus.game.entity.EntityType;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.scheduler.RoomTask;
import org.alexdev.icarus.game.room.user.RoomUser;

public class CarryItemTask extends RoomTask {

    private Room room;

    public CarryItemTask(Room room) {
        this.room = room;
    }

    @Override
    public void execute() {

        if (this.room.getEntityManager().getEntities().size() == 0) {
            return;
        }

        List<Entity> entities = this.room.getEntityManager().getEntitiesByType(EntityType.PLAYER, EntityType.BOT);

        for (int i = 0; i < entities.size(); i++) {

            Entity entity = entities.get(i);

            RoomUser roomUser = entity.getRoomUser();

            if (roomUser.getCarryTimer().get() > 0) {
                roomUser.getCarryTimer().decrementAndGet();
            } else {
                
                if (roomUser.getCarryTimer().get() == 0) {
                    roomUser.getCarryTimer().set(-1);
                    roomUser.carryItem(0);
                }
            }
        }
    }
}
