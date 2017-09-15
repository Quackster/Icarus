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

public class RoomScheduler implements Runnable {
    
    private ScheduledFuture<?> walkingScheduledTask;
    private ScheduledFuture<?> roomScheduledTasks;
    
    private Map<Long, List<RoomTask>> tasks;
    
    private Room room;
    private MovementTask movementTask;
    private AtomicLong counter;

    public RoomScheduler (Room room) {
        this.room = room;
        this.counter = new AtomicLong();
        this.tasks = Maps.newHashMap();
        this.movementTask = new MovementTask(room);
    }

    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        
        try {
     
            for (Entry<Long, List<RoomTask>> kvp : this.tasks.entrySet()) {

                if (this.counter.get() % kvp.getKey() == 0) {    
                    for (RoomTask task : kvp.getValue()) {
                        task.execute();
                    }
                }
            }
            
            this.counter.incrementAndGet();
            
        } catch (Exception e) {
            Log.exception(e);
            this.roomScheduledTasks.cancel(true);
        }
        
    }
    
    /**
     * Schedule tasks, will call the method from {@link Room} to schedule
     * any user defined events.
     */
    public void scheduleTasks() {
        
        this.room.scheduleEvents();
        
        if (this.roomScheduledTasks == null) {
            this.roomScheduledTasks = RoomManager.getScheduledPool().scheduleAtFixedRate(this, 0, 1, TimeUnit.SECONDS);
        }
        
        if (this.walkingScheduledTask == null) {
            this.walkingScheduledTask = RoomManager.getScheduledPool().scheduleAtFixedRate(this.movementTask, 0, 500, TimeUnit.MILLISECONDS);
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
     * @param number the number
     * @param measurement the measurement
     * @param task the task
     */
    public void addScheduleEvent(int number, TimeUnit measurement, RoomTask task) {
        
        long taskInSeconds = measurement.toSeconds(number);
        
        if (!this.tasks.containsKey(taskInSeconds)) {
            this.tasks.put(taskInSeconds, Lists.newArrayList());
        }
        
        this.tasks.get(taskInSeconds).add(task);
    }
}
