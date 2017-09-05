package org.alexdev.icarus.game.pets;

public class PetRace {

    private int raceId;
    private int colour1;
    private int colour2;
    private boolean has1Colour;
    private boolean has2Colour;
        
    public PetRace(int raceId, int colour1, int colour2, boolean has1Colour, boolean has2Colour) {
        this.raceId = raceId;
        this.colour1 = colour1;
        this.colour2 = colour2;
        this.has1Colour = has1Colour;
        this.has2Colour = has2Colour;
    }

    public int getRaceId() {
        return raceId;
    }
    
    public int getColour1() {
        return colour1;
    }
    
    public int getColour2() {
        return colour2;
    }
    
    public boolean hasColour1() {
        return has1Colour;
    }
    
    public boolean hasColour2() {
        return has2Colour;
    }
}
