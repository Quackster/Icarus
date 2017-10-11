package org.alexdev.icarus.game.groups.types;

public class GroupSymbolColour {
    private int id;
    private String colour;

    public GroupSymbolColour(int id, String colour) {
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
