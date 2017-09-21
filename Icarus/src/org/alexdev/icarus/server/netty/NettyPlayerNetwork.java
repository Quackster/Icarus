package org.alexdev.icarus.server.netty;

import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.api.PlayerNetwork;
import org.jboss.netty.channel.Channel;

public class NettyPlayerNetwork extends PlayerNetwork {

    private Channel channel;

    public NettyPlayerNetwork(Channel channel, int connectionId) {
        super(connectionId);
        this.channel = channel;
    }

    @Override
    public void close() {
        channel.close();
    }

    @Override
    public void send(MessageComposer response) {
        channel.write(response);
        
    }
}
