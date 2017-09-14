package org.alexdev.icarus.game.room.scheduler;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.RoomManager;
import org.alexdev.icarus.game.room.tasks.MovementTask;
import org.alexdev.icarus.log.Log;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

public class Scheduler {

    private ScheduledFuture<?> walkingScheduledTask;
    private ScheduledFuture<?> roomScheduledTasks;
    
    private Map<ScheduleTime, List<ScheduledTask>> tasks;
    
    private Room room;
    private MovementTask movementTask;
    private AtomicLong counter;

    public Scheduler (Room room) {
        this.room = room;
        this.counter = new AtomicLong();
        this.tasks = Maps.newHashMap();
        this.movementTask = new MovementTask(room);
    }

    /**
     * Schedule tasks.
     */
    public void scheduleTasks() {

        for (ScheduleTime time : ScheduleTime.values()) {
            this.tasks.put(time, Lists.newArrayList());
        }
        
        this.room.scheduleEvents();
        
        if (this.walkingScheduledTask == null) {
            this.walkingScheduledTask = RoomManager.getScheduler().scheduleAtFixedRate(() -> {
                this.movementTask.execute();
            }, 0, 500, TimeUnit.MILLISECONDS);
        }
        
        if (this.roomScheduledTasks == null) {
            this.roomScheduledTasks = RoomManager.getScheduler().scheduleAtFixedRate(() -> {

                try {
                    
                    for (Entry<ScheduleTime, List<ScheduledTask>> kvp : tasks.entrySet()) {

                        if (counter.get() % kvp.getKey().getSeconds() == 0) {    
                            for (ScheduledTask task : kvp.getValue()) {
                                task.execute();
                            }
                        }
                    }
                    
                    counter.incrementAndGet();
                    
                } catch (Exception e) {
                    Log.println();
                    this.roomScheduledTasks.cancel(true);
                }
                
            }, 0, 1, TimeUnit.SECONDS);
        }
    }

    /**
     * Cancel tasks.
     */
    public void cancelTasks() {

        if (this.walkingScheduledTask != null) {
            this.walkingScheduledTask.cancel(true);
            this.walkingScheduledTask = null;
        }

        if (this.roomScheduledTasks != null) {
            this.roomScheduledTasks.cancel(true);
            this.roomScheduledTasks = null;
        }
        
        this.tasks.clear();
        this.counter.set(0);
    }

    /**
     * Adds the schedule event.
     *
     * @param time the time
     * @param task the task
     */
    public void addScheduleEvent(ScheduleTime time, ScheduledTask task) {
        this.tasks.get(time).add(task);
    }
}
