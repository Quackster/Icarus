package org.alexdev.icarus.server.netty;

import org.alexdev.icarus.messages.MessageComposer;
import org.alexdev.icarus.server.api.IPlayerNetwork;
import org.jboss.netty.channel.Channel;

public class NettyPlayerNetwork extends IPlayerNetwork {

    private Channel channel;

    public NettyPlayerNetwork(Channel channel, int connectionID) {
        super(connectionID);
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
