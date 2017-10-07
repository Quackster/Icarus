package org.alexdev.icarus.game.item.moodlight;

public class MoodlightPreset {
    
    private boolean backgroundOnly = false;
    private String colorCode = "#000000";
    private int colorIntensity = 255;

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
