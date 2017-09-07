package org.alexdev.icarus.game.room.tasks;

import java.util.concurrent.atomic.AtomicLong;

public abstract class RoomTask {
    
    private AtomicLong tick = new AtomicLong(0);
    
    public boolean canTick(int num) {
        return tick.get() % num  == 0;
    }
    
    public void tick() {
        this.tick.incrementAndGet();
    }
    
    public abstract void execute();
}
