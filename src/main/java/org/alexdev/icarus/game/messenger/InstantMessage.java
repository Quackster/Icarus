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

    /**
     * Gets the from id.
     *
     * @return the from id
     */
    public int getFromId() {
        return fromId;
    }
    
    /**
     * Sets the from id.
     *
     * @param fromId the new from id
     */
    public void setFromId(int fromId) {
        this.fromId = fromId;
    }
    
    /**
     * Gets the to id.
     *
     * @return the to id
     */
    public int getToId() {
        return toId;
    }
    
    /**
     * Sets the to id.
     *
     * @param toId the new to id
     */
    public void setToId(int toId) {
        this.toId = toId;
    }
    
    /**
     * Gets the message.
     *
     * @return the message
     */
    public String getMessage() {
        return message;
    }
    
    /**
     * Sets the message.
     *
     * @param message the new message
     */
    public void setMessage(String message) {
        this.message = message;
    }
    
    /**
     * Gets the friend.
     *
     * @return the friend
     */
    public Player getFriend() {
        return PlayerManager.getInstance().getById(this.toId);
    }
}
