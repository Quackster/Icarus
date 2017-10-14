package org.alexdev.icarus.game.room.scheduler;

import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.RoomManager;
import org.alexdev.icarus.game.room.tasks.MovementTask;
import org.alexdev.icarus.log.Log;

public class RoomScheduler implements Runnable {
    
	private Room room;
    private ScheduledFuture<?> walkingScheduledTask;
    private ScheduledFuture<?> roomScheduledTasks;
    
    private Map<Long, ConcurrentLinkedQueue<RoomTask>> tasks;

    private MovementTask movementTask;
    private AtomicLong counter;
    
    private boolean disabled;
	

    public RoomScheduler (Room room) {
    	this.room = room;
        this.tasks = new ConcurrentHashMap<>();
        
        this.counter = new AtomicLong();
        this.movementTask = new MovementTask(room);
    }

    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        
        if (this.disabled) {
            this.cancelTasks();
            return;
        }
        
        try {
     
            for (Entry<Long, ConcurrentLinkedQueue<RoomTask>> kvp : this.tasks.entrySet()) {

                if (this.counter.get() % kvp.getKey() == 0) {
                    
                    for (RoomTask task : kvp.getValue()) {
                        task.execute();
                        
                        if (task.getTaskType() == TaskType.RUN_ONCE) {
                            this.removeTask(task.getClass());
                        }
                    }
                }
            }
            
            this.counter.incrementAndGet();
            
        } catch (Exception e) {
        	Log.getErrorLogger().error("Room scheduler error for room id {} has occurred", this.room.getData().getId(), e);
            this.cancelTasks();
        }
        
    }
    
    /**
     * Schedule tasks, will call the method from {@link Room} to schedule
     * any user defined events.
     */
    public void scheduleTasks() {

        boolean taskStarted = false;
        
        if (this.roomScheduledTasks == null) {
            this.roomScheduledTasks = RoomManager.getScheduledPool().scheduleAtFixedRate(this, 0, 1, TimeUnit.SECONDS);
            taskStarted = true;
        }
        
        if (this.walkingScheduledTask == null) {
            this.walkingScheduledTask = RoomManager.getScheduledPool().scheduleAtFixedRate(this.movementTask, 0, 500, TimeUnit.MILLISECONDS);
            taskStarted = true;
        }
        
        if (taskStarted) {
            this.disabled = false;
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
        this.disabled = true;
    }

    /**
     * Adds the schedule event.
     *
     * @param number the number
     * @param measurement the measurement
     * @param taskType the task type
     * @param task the task
     */
    public void scheduleEvent(int number, TimeUnit measurement,TaskType taskType, RoomTask task) {
        
        task.setTaskType(taskType);
        
        long taskInSeconds = measurement.toSeconds(number);
        
        if (!this.tasks.containsKey(taskInSeconds)) {
            this.tasks.put(taskInSeconds, new ConcurrentLinkedQueue<>());
        }
        
        this.tasks.get(taskInSeconds).add(task);
    }
    
    /**
     * Gets the task by class.
     *
     * @param <T> the generic type
     * @param taskClass the task class
     * @return the task by class
     */
    public <T> T getTaskByClass(Class<T> taskClass) {
        
        for (ConcurrentLinkedQueue<RoomTask> taskDurationList : this.tasks.values()) {
            for (RoomTask task : taskDurationList) {
                if (task.getClass().equals(taskClass)) {
                    return taskClass.cast(task);
                }
            }
        }
        
        return null;
    }
    
    /**
     * Remove the task by class.
     *
     * @param taskClass the task class
     * @return the task by class
     */
    public void removeTask(Class<? extends RoomTask> taskClass) {
        
        for (ConcurrentLinkedQueue<RoomTask> taskDurationList : this.tasks.values()) {
            for (RoomTask task : taskDurationList) {
                if (task.getClass().equals(taskClass)) {
                    taskDurationList.remove(task);
                }
            }
        }
    }


    /**
     * Checks if is disabled.
     *
     * @return true, if is disabled
     */
    public boolean isDisabled() {
        return disabled;
    }

    /**
     * Sets the disabled.
     *
     * @param disabled the new disabled
     */
    public void setDisabled(boolean disabled) {
        this.disabled = disabled;
    }
}
