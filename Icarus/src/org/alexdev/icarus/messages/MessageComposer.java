package org.alexdev.icarus.messages;

import org.alexdev.icarus.server.api.messages.Response;
import org.alexdev.icarus.server.netty.streams.NettyResponse;

public abstract class MessageComposer {
    protected Response response;
    
    public MessageComposer() {
        this.response = new NettyResponse();
    }
    
    public abstract void write();

    public Response getResponse() {
        return response;
    }

    public void setResponse(NettyResponse response) {
        this.response = response;
    }
}
