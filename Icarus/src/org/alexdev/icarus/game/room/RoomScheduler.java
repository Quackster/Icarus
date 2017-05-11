package org.alexdev.icarus.game.room;

import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

import org.alexdev.icarus.game.room.schedulers.RoomWalkScheduler;
import org.alexdev.icarus.log.Log;

public class RoomScheduler {

    private Room room;
    private ScheduledFuture<?> walkingScheduledTask;

    public RoomScheduler (Room room) {
        this.room = room;
    }

    public void scheduleTasks() {

        if  (this.walkingScheduledTask == null) {
            this.walkingScheduledTask = RoomManager.getScheduler().scheduleAtFixedRate(new RoomWalkScheduler(this.room), 0, 500, TimeUnit.MILLISECONDS);
        }
    }

    public void cancelTasks() {

        if (this.walkingScheduledTask != null) {
            this.walkingScheduledTask.cancel(true);
            this.walkingScheduledTask = null;
        }

        Log.println("Tasks have been cleared");


    }
}
