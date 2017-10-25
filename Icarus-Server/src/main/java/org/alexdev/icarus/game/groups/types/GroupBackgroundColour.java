package org.alexdev.icarus.game.groups.types;

public class GroupBackgroundColour {
    
    private int id;
    private String colour;

    public GroupBackgroundColour(int id, String colour) {
        this.id = id;
        this.colour = colour;
    }

    public int getId() {
        return id;
    }

    public String getColour() {
        return colour;
    }
}
