package org.alexdev.icarus.game.entity;

import org.alexdev.icarus.game.player.PlayerDetails;
import org.alexdev.icarus.game.room.Room;
import org.alexdev.icarus.game.room.user.RoomUser;
import org.alexdev.icarus.util.metadata.Metadata;

public abstract class Entity extends Metadata {
    
    protected boolean disposed;
    
    /**
     * Gets the details.
     *
     * @return the details
     */
    public abstract PlayerDetails getDetails();
    
    /**
     * Gets the player id
     * 
     * @return the id
     */
    public int getEntityId() {
        return this.getDetails().getId();
    }
    
    /**
     * Gets the room user.
     *
     * @return the room user
     */
    public abstract RoomUser getRoomUser();
    
    /**
     * Gets the type.
     *
     * @return the type
     */
    public abstract EntityType getType();
    
    /**
     * Dispose.
     */
    public abstract void dispose();

    /**
     * Gets the room.
     *
     * @return the room
     */
    public Room getRoom() {
        return getRoomUser().getRoom();
    }

    /**
     * In room.
     *
     * @return true, if successful
     */
    public boolean inRoom() {
        return getRoomUser().getRoom() != null;
    }
    
    /**
     * Checks if is disposed.
     *
     * @return true, if is disposed
     */
    public boolean isDisposed() {
        return this.disposed;
    }
    
    /**
     * Sets the disposed.
     *
     * @param flag the new disposed
     */
    public void setDisposed(boolean flag) {
        this.disposed = flag;
    }
}
