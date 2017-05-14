package org.alexdev.icarus.game.room;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.alexdev.icarus.game.room.tasks.CarryItemTask;
import org.alexdev.icarus.game.room.tasks.EntityStatusTask;
import org.alexdev.icarus.game.room.tasks.RollerTask;

public class RoomScheduler {

    private Room room;
    private ScheduledFuture<?> walkingScheduledTask;
    private ScheduledFuture<?> roomScheduledTasks;
    
    private RollerTask rollerTask;
    private CarryItemTask carryItemTask;
    
    public RoomScheduler (Room room) {
        this.room = room;
        this.rollerTask = new RollerTask(room);
        this.carryItemTask = new CarryItemTask(room);
    }

    public void scheduleTasks() {

    	// Walking has it's own task because I don't want any overhead
        if (this.walkingScheduledTask == null) {
            this.walkingScheduledTask = RoomManager.getScheduler().scheduleAtFixedRate(new EntityStatusTask(this.room), 0, 500, TimeUnit.MILLISECONDS);
        }
        
        if (this.roomScheduledTasks == null) {
            this.roomScheduledTasks = RoomManager.getScheduler().scheduleAtFixedRate(() -> {
            	this.rollerTask.execute();
            	this.carryItemTask.execute();
            }, 0, 1, TimeUnit.SECONDS);
        }
    }

    public void cancelTasks() {

        if (this.walkingScheduledTask != null) {
            this.walkingScheduledTask.cancel(true);
            this.walkingScheduledTask = null;
        }
        
        if (this.roomScheduledTasks != null) {
            this.roomScheduledTasks.cancel(true);
            this.roomScheduledTasks = null;
        }
    }
}
