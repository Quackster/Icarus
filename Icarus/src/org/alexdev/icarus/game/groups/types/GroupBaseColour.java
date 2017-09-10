package org.alexdev.icarus.game.groups.types;

public class GroupBaseColour {
    private int id;
    private String colour;

    public GroupBaseColour(int id, String colour) {
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
