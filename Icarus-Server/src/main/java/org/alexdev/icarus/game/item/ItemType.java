package org.alexdev.icarus.game.item;

public enum ItemType {
    FLOOR("s"),
    WALL("i"),
    BADGE("b"),
    EFFECT("e"),
    BOT("r"),
    PET("p"),
    CLUB("h");
    
    private String type;

    ItemType(String type) {
        this.type = type;
    }
    
    public static ItemType getType(String typeName) {
        
        for (ItemType type : ItemType.values()) {
            if (type.toString().equals(typeName)) {
                return type;
            }
        }
        
        return null;
    }
    
    @Override
    public String toString() {
        return this.type;
    }
}
