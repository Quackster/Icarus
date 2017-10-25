package org.alexdev.icarus.game.item.extradata;

public enum ExtraDataType {
    STRING(0),
    STRING_ARRAY(2),
    KEY_VALUE(1),
    INT_ARRAY(5);
    
    private int type;
    
    ExtraDataType(int type) {
        this.type = type;
    }

    public int getTypeId() {
        return type;
    }
}
