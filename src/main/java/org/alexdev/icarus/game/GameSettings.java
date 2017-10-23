package org.alexdev.icarus.game;

import org.alexdev.icarus.game.item.interactions.Interaction;
import org.alexdev.icarus.util.Util;

public class GameSettings {

    public static int MAX_ROOMS_PER_ACCOUNT;
    public static int MAX_ROOMS_POPULAR;
    public static int MAX_ROOMS_SUB_CATEGORIES;

    public static int CREDITS_INTERVAL_MINUTES;
    public static int CREDITS_INTERVAL_AMOUNT;

    public static int DUCKETS_INTERVAL_MINUTES;
    public static int DUCKETS_INTERVAL_AMOUNT;

    public static int CHAT_FLOOD_SECONDS = 4;
    public static int CHAT_FLOOD_WAIT = 20;
    public static int MAX_CHAT_BEFORE_FLOOD = 8;

    public static final double FURNITURE_OFFSET = 0.001;

    public static void load() {
        MAX_ROOMS_PER_ACCOUNT = Util.getGameConfig().get("Navigator", "max.room.per.user", Integer.class);
        MAX_ROOMS_POPULAR = Util.getGameConfig().get("Navigator", "max.rooms.popular.tab", Integer.class);
        MAX_ROOMS_SUB_CATEGORIES = Util.getGameConfig().get("Navigator", "max.room.sub.category", Integer.class);
        CREDITS_INTERVAL_MINUTES = Util.getGameConfig().get("Scheduler", "credits.interval.minutes", Integer.class);
        CREDITS_INTERVAL_AMOUNT = Util.getGameConfig().get("Scheduler", "credits.interval.amount", Integer.class);
        DUCKETS_INTERVAL_MINUTES = Util.getGameConfig().get("Scheduler", "duckets.interval.minutes", Integer.class);
        DUCKETS_INTERVAL_AMOUNT = Util.getGameConfig().get("Scheduler", "duckets.interval.amount", Integer.class);
    }
}
