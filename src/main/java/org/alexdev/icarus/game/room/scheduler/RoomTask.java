package org.alexdev.icarus.game.room.scheduler;

public abstract class RoomTask {
    
    private TaskType taskType;
    
    /**
     * Execute the task.
     */
    public abstract void execute();

    /**
     * Gets the task type.
     *
     * @return the task type
     */
    public TaskType getTaskType() {
        return taskType;
    }

    /**
     * Sets the task type.
     *
     * @param taskType the new task type
     */
    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }
}
