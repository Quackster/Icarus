package org.alexdev.icarus.game.item.json;

public class MannequinData {

    private String gender = "m";
    private String figure = ".ch-210-1321.lg-285-92";
    private String outfitName = "Default Mannequin";
    
    /**
     * Gets the gender.
     *
     * @return the gender
     */
    public String getGender() {
        return gender;
    }
    
    /**
     * Sets the gender.
     *
     * @param gender the new gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }
    
    /**
     * Gets the figure.
     *
     * @return the figure
     */
    public String getFigure() {
        return figure;
    }
    
    /**
     * Sets the figure.
     *
     * @param figure the new figure
     */
    public void setFigure(String figure) {
        this.figure = figure;
    }
    
    /**
     * Gets the outfit name.
     *
     * @return the outfit name
     */
    public String getOutfitName() {
        return outfitName;
    }
    
    /**
     * Sets the outfit name.
     *
     * @param outfitName the new outfit name
     */
    public void setOutfitName(String outfitName) {
        this.outfitName = outfitName;
    }
}
