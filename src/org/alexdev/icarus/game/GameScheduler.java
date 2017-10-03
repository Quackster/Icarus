package org.alexdev.icarus.game;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.RoomManager;

public class GameScheduler implements Runnable {

    private static AtomicLong tickRate = new AtomicLong();
    private static ScheduledExecutorService scheduler;
    private static ScheduledFuture<?> gameScheduler;
    
    /**
     * Load.
     */
    public static void load() {
        scheduler = createNewScheduler();
        gameScheduler = scheduler.scheduleAtFixedRate(new GameScheduler(), 0, 1, TimeUnit.SECONDS);
    }
    
    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {
        
        tickRate.incrementAndGet();
        
        // If this task has ticked for an entire minute...
        if (tickRate.get() % 60 == 0) {
            for (Room room : RoomManager.getPromotedRooms()) {
                room.getPromotion().performCycle();
            }
        }
    }

    /**
     * Gets the scheduler.
     *
     * @return the scheduler
     */
    public static ScheduledExecutorService getScheduler() {
        return scheduler;
    }
    
    /**
     * Gets the game scheduler.
     *
     * @return the game scheduler
     */
    public static ScheduledFuture<?> getGameScheduler() {
        return gameScheduler;
    }

    /**
     * Creates the new scheduler.
     *
     * @return the scheduled executor service
     */
    public static ScheduledExecutorService createNewScheduler() {
        return Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors() * 2);
    }
}
