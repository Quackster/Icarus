package org.alexdev.icarus.messages.parsers;

import org.alexdev.icarus.server.netty.streams.NettyResponse;

public abstract class MessageComposer {
    protected NettyResponse response;
    
    public MessageComposer() {
        this.response = new NettyResponse();
    }
    
    public abstract void write();

    public NettyResponse getResponse() {
        return response;
    }

    public void setResponse(NettyResponse response) {
        this.response = response;
    }
}
