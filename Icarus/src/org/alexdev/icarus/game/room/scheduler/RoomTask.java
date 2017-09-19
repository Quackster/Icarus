package org.alexdev.icarus.game.room.scheduler;

public interface RoomTask {
    
    /**
     * Execute the task.
     */
    public void execute();

    /**
     * Gets the type of thread, whether it's run once or always occurring.
     * @return
     */
    public TaskType getType();
    
    /**
     * Sets the thread type.
     *
     * @param type the new thread type
     */
    public void setThreadType(TaskType type);
}
