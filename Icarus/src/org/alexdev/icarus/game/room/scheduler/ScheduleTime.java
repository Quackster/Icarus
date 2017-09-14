package org.alexdev.icarus.game.room.scheduler;

public enum ScheduleTime {

    ONE_SECOND(1),
    FOUR_SECONDS(4),
    FIVE_SECONDS(5);
    
    private int seconds;

    ScheduleTime(int seconds) {
        this.seconds = seconds;
    }

    public int getSeconds() {
        return seconds;
    }
    
    
}
