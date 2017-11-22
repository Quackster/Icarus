package org.alexdev.icarus.game;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.player.PlayerManager;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.RoomManager;

public class GameScheduler implements Runnable {

    private AtomicLong tickRate = new AtomicLong();

    private ScheduledExecutorService scheduler;
    private ScheduledFuture<?> gameScheduler;

    private static GameScheduler instance;

    public GameScheduler() {
        scheduler = createNewScheduler();
        gameScheduler = scheduler.scheduleAtFixedRate(this, 0, 1, TimeUnit.SECONDS);
    }
    
    /* (non-Javadoc)
     * @see java.lang.Runnable#run()
     */
    @Override
    public void run() {

        tickRate.incrementAndGet();

        // If this task has ticked for an entire minute...
        if (tickRate.get() % 60 == 0) {
            for (Room room : RoomManager.getInstance().getPromotedRooms()) {
                room.getPromotion().performCycle();
            }
        }

        if (GameSettings.CREDITS_INTERVAL_AMOUNT > 0) {
            // If this task has ticked for an entire specified number of minutes...
            if (tickRate.get() % (60 * GameSettings.CREDITS_INTERVAL_MINUTES) == 0) {
                for (Player player : PlayerManager.getInstance().getPlayers()) {
                    player.getDetails().setCredits(player.getDetails().getCredits() + GameSettings.CREDITS_INTERVAL_AMOUNT);
                    player.getDetails().sendCredits();
                    player.getDetails().save();
                }
            }
        }

        if (GameSettings.DUCKETS_INTERVAL_AMOUNT > 0) {
            // If this task has ticked for an entire specified number of minutes...
            if (tickRate.get() % (60 * GameSettings.DUCKETS_INTERVAL_MINUTES) == 0) {
                for (Player player : PlayerManager.getInstance().getPlayers()) {
                    player.getDetails().setDuckets(player.getDetails().getDuckets() + GameSettings.DUCKETS_INTERVAL_AMOUNT);
                    player.getDetails().sendDuckets();
                    player.getDetails().save();
                }
            }
        }
    }

    /**
     * Gets the scheduler.
     *
     * @return the scheduler
     */
    public ScheduledExecutorService getScheduler() {
        return scheduler;
    }
    
    /**
     * Gets the game scheduler.
     *
     * @return the game scheduler
     */
    public ScheduledFuture<?> getGameScheduler() {
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

    /**
     * Gets the instance
     *
     * @return the instance
     */
    public static GameScheduler getInstance() {

        if (instance == null) {
            instance = new GameScheduler();
        }

        return instance;
    }
}
