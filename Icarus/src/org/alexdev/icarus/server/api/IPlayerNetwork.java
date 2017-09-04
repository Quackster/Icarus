package org.alexdev.icarus.server.api;

import org.alexdev.icarus.messages.parsers.MessageComposer;

public abstract class IPlayerNetwork {

    private int connectionId;
    
    public IPlayerNetwork(int connectionId) {
        this.connectionId = connectionId;
    }
    
    public abstract void send(MessageComposer response);
    public abstract void close();
    
    public int getConnectionId() {
        return connectionId;
    }

    public void setConnectionId(int connectionId) {
        this.connectionId = connectionId;
    }
}
