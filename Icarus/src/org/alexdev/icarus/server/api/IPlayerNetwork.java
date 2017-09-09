package org.alexdev.icarus.server.api;

import org.alexdev.icarus.messages.MessageComposer;

public abstract class IPlayerNetwork {

    private int connectionID;
    
    public IPlayerNetwork(int connectionID) {
        this.connectionID = connectionID;
    }
    
    public abstract void send(MessageComposer response);
    public abstract void close();
    
    public int getConnectionID() {
        return connectionID;
    }

    public void setConnectionID(int connectionID) {
        this.connectionID = connectionID;
    }
}
