package org.alexdev.icarus.game.entity;

public enum EntityStatus {

    MOVE("mv"),
    SIT("sit"),
    LAY("lay"), 
    FLAT_CONTROL("flatctrl"), 
    DANCE("dance");
    
    private String statusCode;

    EntityStatus(String statusCode) {
        this.statusCode = statusCode;
    }

    public String getStatusCode() {
        return statusCode;
    }
}
