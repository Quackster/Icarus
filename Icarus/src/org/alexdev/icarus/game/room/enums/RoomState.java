package org.alexdev.icarus.game.room.enums;

public enum RoomState {
    
    OPEN(0),
    DOORBELL(1),
    PASSWORD(2),
    INVISIBLE(3);
    
    private int stateCode;

    RoomState(int stateCode) {
        this.stateCode = stateCode;
    }
    
    public int getStateCode() {
        return stateCode;
    }
    
    public static RoomState getState(int stateCode) {
        
        for (RoomState state : values()) {
            if (state.getStateCode() == stateCode) {
                return state;
            }
        }
        
        return RoomState.OPEN;
    }
}
