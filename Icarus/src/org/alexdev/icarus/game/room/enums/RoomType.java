package org.alexdev.icarus.game.room.enums;

public enum RoomType {
    
    PUBLIC(1),
    PRIVATE(0);
    
    private int typeCode;

    RoomType(int typeCode) {
        this.typeCode = typeCode;
    }
    
    public int getTypeCode() {
        return typeCode;
    }
    
    public static RoomType getType(int type) {
        
        for (RoomType state : values()) {
            if (state.getTypeCode() == type) {
                return state;
            }
        }
        
        return RoomType.PRIVATE;
    }
}
