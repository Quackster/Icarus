package org.alexdev.icarus.server.netty;

import java.net.InetSocketAddress;
import java.util.concurrent.Executors;

import org.alexdev.icarus.log.Log;
import org.alexdev.icarus.server.api.ServerHandler;
import org.alexdev.icarus.server.netty.codec.NetworkDecoder;
import org.alexdev.icarus.server.netty.codec.NetworkEncoder;
import org.alexdev.icarus.server.netty.connections.ConnectionHandler;
import org.jboss.netty.bootstrap.ServerBootstrap;
import org.jboss.netty.channel.ChannelException;
import org.jboss.netty.channel.ChannelPipeline;
import org.jboss.netty.channel.socket.nio.NioServerSocketChannelFactory;

public class NettyServer extends ServerHandler {

    @Override
    public boolean listenSocket() {

        NioServerSocketChannelFactory factory = new NioServerSocketChannelFactory (
                Executors.newCachedThreadPool(),
                Executors.newCachedThreadPool()
        );
        
        ServerBootstrap bootstrap = new ServerBootstrap(factory);
        ChannelPipeline pipeline = bootstrap.getPipeline();

        pipeline.addLast("gameEncoder", new NetworkEncoder());
        pipeline.addLast("gameDecoder", new NetworkDecoder());
        pipeline.addLast("handler", new ConnectionHandler());
        
        try {
            bootstrap.bind(new InetSocketAddress(this.getIp(), this.getPort()));
        } catch (ChannelException ex) {
            Log.exception(ex);
            return false;
        }

        return true;
    }
}
