package org.alexdev.icarus.server.api;

import org.alexdev.icarus.messages.MessageComposer;

public abstract class PlayerNetwork {

    private int connectionId;
 
    public PlayerNetwork(int connectionId) {
        this.connectionId = connectionId;
    }
    
    /**
     * Send new composer.
     *
     * @param response the response
     */
    public abstract void send(MessageComposer response);
    
    /**
     * Close the connection.
     */
    public abstract void close();
    
    /**
     * Gets the connection id.
     *
     * @return the connection id
     */
    public int getConnectionId() {
        return connectionId;
    }

    /**
     * Sets the connection id.
     *
     * @param connectionId the new connection id
     */
    public void setConnectionId(int connectionId) {
        this.connectionId = connectionId;
    }
}
