package org.alexdev.icarus.game.room;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.alexdev.icarus.game.room.tasks.CarryItemTask;
import org.alexdev.icarus.game.room.tasks.MovementTask;
import org.alexdev.icarus.game.room.tasks.PetTask;
import org.alexdev.icarus.game.room.tasks.RollerTask;

public class RoomScheduler {

    private ScheduledFuture<?> walkingScheduledTask;
    private ScheduledFuture<?> roomScheduledTasks;
    
    private MovementTask movementTask;
    private RollerTask rollerTask;
    private CarryItemTask carryItemTask;
    private PetTask petTask;
    
    public RoomScheduler (Room room) {
        this.rollerTask = new RollerTask(room);
        this.carryItemTask = new CarryItemTask(room);
        this.movementTask =  new MovementTask(room);
        this.petTask = new PetTask(room);
    }

    public void scheduleTasks() {

        // Walking has it's own task because I don't want any overhead
        if (this.walkingScheduledTask == null) {
            this.walkingScheduledTask = RoomManager.getScheduler().scheduleAtFixedRate(() -> {
            	this.movementTask.execute();
            }, 0, 500, TimeUnit.MILLISECONDS);
        }
        
        // Misc tasks
        if (this.roomScheduledTasks == null) {
            this.roomScheduledTasks = RoomManager.getScheduler().scheduleAtFixedRate(() -> {
                this.rollerTask.execute();
                this.carryItemTask.execute();
                this.petTask.execute();
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
