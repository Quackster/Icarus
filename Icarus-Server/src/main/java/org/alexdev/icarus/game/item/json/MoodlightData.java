package org.alexdev.icarus.game.item.json;

import java.util.ArrayList;
import java.util.List;

public class MoodlightData {

    private boolean enabled;
    private int currentPreset = 1;
    private List<MoodlightPreset> presets;
    
    public MoodlightData() {
        this.presets = new ArrayList<>();
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
     * Gets the presets.
     *
     * @return the presets
     */
    public List<MoodlightPreset> getPresets() {
        return presets;
    }
}
