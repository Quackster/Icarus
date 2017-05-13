package org.alexdev.icarus.game.room.schedulers;

import java.util.List;

import org.alexdev.icarus.game.entity.Entity;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.RoomUser;

public class RoomTaskScheduler implements Runnable {

    private Room room;

    public RoomTaskScheduler(Room room) {
        this.room = room;
    }

    @Override
    public void run() {

        try {

            if (this.room.getEntities().size() == 0) {
                return;
            }

            List<Entity> entities = this.room.getEntities();

            for (int i = 0; i < entities.size(); i++) {
                
                Entity entity = entities.get(i);
                
                RoomUser roomUser = entity.getRoomUser();

                if (roomUser.getLookResetTime() > 0) {
                    roomUser.setLookResetTime(roomUser.getLookResetTime() - 1);
                } else {
                    if (roomUser.getLookResetTime() == 0) {
                        roomUser.getPosition().setHeadRotation(roomUser.getPosition().getRotation());
                        roomUser.setLookResetTime(-1);
                        roomUser.setNeedUpdate(true);
                    }
                }
                
            }
            
        } catch (Exception e) { e.printStackTrace(); }
    }

}
