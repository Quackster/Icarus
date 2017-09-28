package org.alexdev.icarus.messages.types;

import org.alexdev.icarus.server.api.messages.Response;
import org.alexdev.icarus.server.netty.streams.NettyResponse;

public abstract class MessageComposer {
    
    protected Response response;
    
    public MessageComposer() {
        this.response = new NettyResponse();
    }
    
    /**
     * Write the message to send back to the client.
     */
    public abstract void write();

    /**
     * Gets the response.
     *
     * @return the response
     */
    public Response getResponse() {
        return response;
    }
}
