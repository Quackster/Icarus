package org.alexdev.icarus.game.room.tasks;

import java.util.List;

import org.alexdev.icarus.game.entity.Entity;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.RoomUser;

public class CarryItemTask extends RoomTask {

    private Room room;
    
    public CarryItemTask(Room room) {
        this.room = room;
    }
    
    public void execute() {

        try {

            if (room.getEntities().size() == 0) {
                return;
            }

            List<Entity> entities = room.getEntities();

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
            
        } catch (Exception e) { e.printStackTrace(); }
        
        this.tick();
    }

}
