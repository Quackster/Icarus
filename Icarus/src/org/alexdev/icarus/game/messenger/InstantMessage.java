package org.alexdev.icarus.game.messenger;

import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.player.PlayerManager;

public class InstantMessage {

    public int fromId;
    public int toId;
    public String message;
    
    public InstantMessage(int fromId, int toId, String message) {
        this.fromId = fromId;
        this.toId = toId;
        this.message = message;
    }

    public int getFromId() {
        return fromId;
    }
    
    public void setFromId(int fromId) {
        this.fromId = fromId;
    }
    
    public int getToId() {
        return toId;
    }
    
    public void setToId(int toId) {
        this.toId = toId;
    }
    
    public String getMessage() {
        return message;
    }
    
    public void setMessage(String message) {
        this.message = message;
    }
    
    public Player getFriend() {
        return PlayerManager.getById(this.toId);
    }
}
