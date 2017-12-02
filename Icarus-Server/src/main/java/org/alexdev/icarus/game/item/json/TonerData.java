package org.alexdev.icarus.game.item.json;

public class TonerData {

    private int hue;
    private int saturation;
    private int brightness;
    private boolean enabled;
    
    /**
     * Gets the hue.
     *
     * @return the hue
     */
    public int getHue() {
        return hue;
    }
    
    /**
     * Sets the hue.
     *
     * @param hue the new hue
     */
    public void setHue(int hue) {
        this.hue = hue;
    }
    
    /**
     * Gets the saturation.
     *
     * @return the saturation
     */
    public int getSaturation() {
        return saturation;
    }
    
    /**
     * Sets the saturation.
     *
     * @param saturation the new saturation
     */
    public void setSaturation(int saturation) {
        this.saturation = saturation;
    }
    
    /**
     * Gets the brightness.
     *
     * @return the brightness
     */
    public int getBrightness() {
        return brightness;
    }
    
    /**
     * Sets the brightness.
     *
     * @param brightness the new brightness
     */
    public void setBrightness(int brightness) {
        this.brightness = brightness;
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
}
