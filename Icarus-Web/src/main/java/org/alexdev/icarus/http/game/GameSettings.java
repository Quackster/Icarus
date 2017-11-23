package org.alexdev.icarus.http.game;

import org.alexdev.icarus.dao.site.SiteKey;
import org.alexdev.icarus.http.mysql.dao.SiteDao;

public class GameSettings {

    public static int MAX_ROOMS_PER_USER;
    public static int MAX_ROOMS_POPULAR_TAB;
    public static int MAX_ROOMS_SUB_CATEGORY;

    public static int CREDITS_INTERVAL_MINUTES;
    public static int CREDITS_INTERVAL_AMOUNT;

    public static int DUCKETS_INTERVAL_MINUTES;
    public static int DUCKETS_INTERVAL_AMOUNT;

    public static boolean BOT_SPAMMERS_ALLOW;
    public static String BOT_SPAMMERS_SSO_PREFIX;

    public static boolean THUMBNAIL_ENABLED;
    public static String THUMBNAIL_FILENAME;
    public static String THUMBNAIL_PATH;
    public static String THUMBNAIL_URL;

    public static boolean CAMERA_ENABLED;
    public static String CAMERA_FILENAME;
    public static String CAMERA_PATH;

    public static final double FURNITURE_OFFSET = 0.001;

    private static GameSettings instance;

    public GameSettings() {
        this.reload();
    }

    /**
     * Reload site config settings
     */
    public void reload() {
        MAX_ROOMS_PER_USER = SiteDao.getInt(SiteKey.MAX_ROOMS_PER_USER);
        MAX_ROOMS_POPULAR_TAB = SiteDao.getInt(SiteKey.MAX_ROOMS_POPULAR_TAB);
        MAX_ROOMS_SUB_CATEGORY = SiteDao.getInt(SiteKey.MAX_ROOMS_SUB_CATEGORY);
        CREDITS_INTERVAL_MINUTES = SiteDao.getInt(SiteKey.CREDITS_INTERVAL_MINUTES);
        CREDITS_INTERVAL_AMOUNT = SiteDao.getInt(SiteKey.CREDITS_INTERVAL_AMOUNT);
        DUCKETS_INTERVAL_MINUTES = SiteDao.getInt(SiteKey.DUCKETS_INTERVAL_MINUTES);
        DUCKETS_INTERVAL_AMOUNT = SiteDao.getInt(SiteKey.DUCKETS_INTERVAL_AMOUNT);
        BOT_SPAMMERS_ALLOW = SiteDao.getBoolean(SiteKey.BOT_SPAMMERS_ALLOW);
        BOT_SPAMMERS_SSO_PREFIX = SiteDao.get(SiteKey.BOT_SPAMMERS_TICKET_PREFIX);
        CAMERA_ENABLED = SiteDao.getBoolean(SiteKey.CAMERA_ENABLED);
        CAMERA_FILENAME = SiteDao.get(SiteKey.CAMERA_FILENAME);
        CAMERA_PATH = SiteDao.get(SiteKey.CAMERA_PATH);
        THUMBNAIL_ENABLED = SiteDao.getBoolean(SiteKey.THUMBNAIL_ENABLED);
        THUMBNAIL_FILENAME = SiteDao.get(SiteKey.THUMBNAIL_FILENAME);
        THUMBNAIL_PATH = SiteDao.get(SiteKey.THUMBNAIL_PATH);
        THUMBNAIL_URL = SiteDao.get(SiteKey.THUMBNAIL_URL);
    }

    /**
     * Get game settings instance
     *
     * @return the instance
     */
    public static GameSettings getInstance() {

        if (instance == null) {
            instance = new GameSettings();
        }

        return instance;
    }
}
