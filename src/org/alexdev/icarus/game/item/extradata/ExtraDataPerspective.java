package org.alexdev.icarus.game.item.extradata;

public enum ExtraDataPerspective {
    FURNI(0),
    WALL_DECORATION(2),
    FLOOR_DECORATION(3),
    OUTSIDE_DECORATION(4);
    
    private int decoration;
    
    ExtraDataPerspective(int decoration) {
        this.decoration = decoration;
    }

    public int getIdentifier() {
        return decoration;
    }

}
