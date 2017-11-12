package org.alexdev.icarus.http.game.site;

public enum SiteKey {

    AFK_ROOM_KICK("afk.room.kick"),
    BOT_SPAMMERS_ALLOW("bot.spammers.allow"),
    BOT_SPAMMERS_TICKET_PREFIX("bot.spammers.ticket.prefix"),
    CAMERA_ENABLED("camera.enabled"),
    CAMERA_FILENAME("camera.filename"),
    CAMERA_PATH("camera.path"),
    CREDITS_INTERVAL_AMOUNT("credits.interval.amount"),
    CREDITS_INTERVAL_MINUTES("credits.interval.minutes"),
    DUCKETS_INTERVAL_AMOUNT("duckets.interval.amount"),
    DUCKETS_INTERVAL_MINUTES("duckets.interval.minutes"),
    MAX_ROOMS_PER_USER("max.rooms.per.user"),
    MAX_ROOMS_SUB_CATEGORY("max.rooms.sub.category"),
    MAX_ROOMS_POPULAR_TAB("max.rooms.popular.tab"),
    THUMBNAIL_ENABLED("thumbnail.enabled"),
    THUMBNAIL_FILENAME("thumbnail.filename"),
    THUMBNAIL_PATH("thumbnail.path"),
    THUMBNAIL_URL("thumbnail.url"),
    USERS_ONLINE("users.online");

    private final String key;

    SiteKey(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }
}
