package org.alexdev.icarus.game.room.tasks;

public abstract class RoomTask {
    
    private long tick = 0;
    
    public boolean canTick(int num) {
        return tick % num  == 0;
    }
    
    public void tick() {
        this.tick++;
    }
    
    public abstract void execute();
}
