package org.alexdev.icarus.game.item.moodlight;

import org.alexdev.icarus.dao.mysql.room.MoodlightDao;

public class MoodlightManager {
    
    /**
     * Gets the moodlight data.
     *
     * @param itemId the item id
     * @return the moodlight data
     */
    public static MoodlightData getMoodlightData(int itemId) {
        
        MoodlightData data = null;
        
        if (!MoodlightDao.hasMoodlightData(itemId)) {
            MoodlightDao.newMoodlightData(itemId);
        }
        
        data = MoodlightDao.getMoodlightData(itemId);
        
        return data;
    }
    
    /**
     * Checks if is valid intensity.
     *
     * @param intensity the intensity
     * @return true, if is valid intensity
     */
    public static boolean isValidIntensity(int intensity) {
        
        if (intensity < 0 || intensity > 255) {
            return false;
        }

        return true;
    }
    
    /**
     * Checks if is valid color.
     *
     * @param colorCode the color code
     * @return true, if is valid color
     */
    public static boolean isValidColor(String colorCode) {
        
        switch (colorCode) {
            case "#000000":
            case "#0053F7":
            case "#EA4532":
            case "#82F349":
            case "#74F5F5":
            case "#E759DE":
            case "#F2F851":
                return true;
            default:
                return false;
        }
    }
}
