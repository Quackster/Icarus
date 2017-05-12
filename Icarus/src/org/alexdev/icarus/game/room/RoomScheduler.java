package org.alexdev.icarus.game.room;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.alexdev.icarus.game.room.schedulers.RoomTaskScheduler;
import org.alexdev.icarus.game.room.schedulers.RoomWalkScheduler;

public class RoomScheduler {

    private Room room;
    private ScheduledFuture<?> walkingScheduledTask;
    private ScheduledFuture<?> roomScheduledTasks;
    
    public RoomScheduler (Room room) {
        this.room = room;
    }

    public void scheduleTasks() {

        if (this.walkingScheduledTask == null) {
            this.walkingScheduledTask = RoomManager.getScheduler().scheduleAtFixedRate(new RoomWalkScheduler(this.room), 0, 500, TimeUnit.MILLISECONDS);
        }
        
        if (this.roomScheduledTasks == null) {
            this.roomScheduledTasks = RoomManager.getScheduler().scheduleAtFixedRate(new RoomTaskScheduler(this.room), 0, 1, TimeUnit.SECONDS);
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
