package org.alexdev.icarus.server.api;

import org.alexdev.icarus.messages.types.MessageComposer;

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
     * Send new composer in a queue.
     *
     * @param response the response
     */
    public abstract void sendQueued(MessageComposer response);
    
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

    /**
     * Adds the pipeline stage.
     *
     * @param object the object
     */
    public abstract void addPipelineStage(Object object);

    /**
     * Flush all data written to socket.
     */
    public abstract void flush();
   
}
