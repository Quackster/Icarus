package org.alexdev.icarus.messages.types;

import io.netty.buffer.ByteBuf;
import org.alexdev.icarus.server.api.messages.Response;
import org.alexdev.icarus.server.netty.streams.NettyResponse;

public abstract class MessageComposer {

    /**
     * Write the message to send back to the client.
     */
    public abstract void compose(Response response);

    /**
     * Write to buffer
     */
    public Response writeToBuffer(ByteBuf buf) {

        return null;
    }

    /**
     * Get the header
     */
    public abstract short getHeader();
}
