package org.alexdev.icarus.game.item.moodlight;

public class MoodlightPreset {
    
    private boolean backgroundOnly;
    private String colorCode;
    private int colorIntensity;
    
    public MoodlightPreset(String presetData) {
        
        String[] parts = presetData.split(",");
        
        if (!MoodlightManager.isValidColor(parts[0])) {
            parts[0] = "#000000";
        }
        
        this.colorCode = parts[0];
        this.colorIntensity = Integer.valueOf(parts[1]);
        this.backgroundOnly = Integer.valueOf(parts[2]) == 1;
    }
    
    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return this.colorCode + "," + this.colorIntensity + "," + (this.backgroundOnly ? 1 : 0);
    }

    /**
     * Checks if is background only.
     *
     * @return true, if is background only
     */
    public boolean isBackgroundOnly() {
        return backgroundOnly;
    }

    /**
     * Sets the background only.
     *
     * @param backgroundOnly the new background only
     */
    public void setBackgroundOnly(boolean backgroundOnly) {
        this.backgroundOnly = backgroundOnly;
    }

    /**
     * Gets the color code.
     *
     * @return the color code
     */
    public String getColorCode() {
        return colorCode;
    }

    /**
     * Sets the color code.
     *
     * @param colorCode the new color code
     */
    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    /**
     * Gets the color intensity.
     *
     * @return the color intensity
     */
    public int getColorIntensity() {
        return colorIntensity;
    }

    /**
     * Sets the color intensity.
     *
     * @param colorIntensity the new color intensity
     */
    public void setColorIntensity(int colorIntensity) {
        this.colorIntensity = colorIntensity;
    }

}
