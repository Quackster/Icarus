package org.alexdev.icarus.game.item.moodlight;

import java.util.List;

import org.alexdev.icarus.dao.mysql.room.MoodlightDao;

import com.google.common.collect.Lists;

public class MoodlightData {

    private int id;
    private int currentPreset;
    private boolean enabled;
    private List<MoodlightPreset> presets;
    
    public MoodlightData(int id, int currentPresent, boolean enabled, String preset1, String preset2, String preset3) {
        this.id = id;
        this.currentPreset = currentPresent;
        this.enabled = enabled;
        
        this.presets = Lists.newArrayList();
        this.presets.add(new MoodlightPreset(preset1));
        this.presets.add(new MoodlightPreset(preset2));
        this.presets.add(new MoodlightPreset(preset3));
    }
    
    /**
     * Generate extra data.
     *
     * @return the string
     */
    public String generateExtraData() {
        
        MoodlightPreset preset = this.presets.get(this.currentPreset - 1);
        
        StringBuilder builder = new StringBuilder();
        builder.append(enabled == true ? 2 : 1);
        builder.append(",");
        builder.append(currentPreset);
        builder.append(",");
        builder.append(preset.isBackgroundOnly() ? 2 : 1);
        builder.append(",");
        builder.append(preset.getColorCode());
        builder.append(",");
        builder.append(preset.getColorIntensity());
        
        return builder.toString();
    }
    
    /**
     * Save.
     */
    public void save() {
        MoodlightDao.saveMoodlightData(this);
    }
    
    /**
     * Gets the id.
     *
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * Gets the current preset.
     *
     * @return the current preset
     */
    public int getCurrentPreset() {
        return currentPreset;
    }

    /**
     * Sets the current preset.
     *
     * @param currentPreset the new current preset
     */
    public void setCurrentPreset(int currentPreset) {
        this.currentPreset = currentPreset;
    }

    /**
     * Checks if is enabled.
     *
     * @return true, if is enabled
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Sets the enabled.
     *
     * @param enabled the new enabled
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Gets the presets.
     *
     * @return the presets
     */
    public List<MoodlightPreset> getPresets() {
        return presets;
    }
}
