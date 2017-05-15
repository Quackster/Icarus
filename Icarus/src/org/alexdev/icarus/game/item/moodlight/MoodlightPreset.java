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
    
    @Override
    public String toString() {
        return this.colorCode + "," + this.colorIntensity + "," + (this.backgroundOnly ? 1 : 0);
    }

    public boolean isBackgroundOnly() {
        return backgroundOnly;
    }

    public void setBackgroundOnly(boolean backgroundOnly) {
        this.backgroundOnly = backgroundOnly;
    }

    public String getColorCode() {
        return colorCode;
    }

    public void setColorCode(String colorCode) {
        this.colorCode = colorCode;
    }

    public int getColorIntensity() {
        return colorIntensity;
    }

    public void setColorIntensity(int colorIntensity) {
        this.colorIntensity = colorIntensity;
    }

}
