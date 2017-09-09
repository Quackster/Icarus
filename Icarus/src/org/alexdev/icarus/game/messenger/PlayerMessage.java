package org.alexdev.icarus.game.messenger;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.player.PlayerManager;

public class PlayerMessage {

    public int fromID;
    public int toID;
    public String message;
    
    public PlayerMessage(int fromID, int toID, String message) {
        this.fromID = fromID;
        this.toID = toID;
        this.message = message;
    }

    public int getFromID() {
        return fromID;
    }
    
    public void setFromID(int fromID) {
        this.fromID = fromID;
    }
    
    public int getToID() {
        return toID;
    }
    
    public void setToID(int toID) {
        this.toID = toID;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public Player getFriend() {
        return PlayerManager.getByID(this.toID);
    }
}
