package org.alexdev.icarus.game.groups.access;

public enum GroupAccessType {
    
    OPEN(0),
    LOCKED(1),
    PRIVATE(2);
    
    int type = 0;
    
    GroupAccessType(int type) {
        this.type = type;
    }
    
    public int getType() {
        return type;
    }

    public static GroupAccessType getTypeById(int integer) {
        
        if (integer == LOCKED.getType()) {
            return LOCKED;
        }
        
        if (integer == PRIVATE.getType()) {
            return PRIVATE;
        }
        
        return OPEN;
    }
}
