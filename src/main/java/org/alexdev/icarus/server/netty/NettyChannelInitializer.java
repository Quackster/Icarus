package org.alexdev.icarus.server.netty;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import org.alexdev.icarus.server.netty.codec.NetworkDecoder;
import org.alexdev.icarus.server.netty.codec.NetworkEncoder;
import org.alexdev.icarus.server.netty.connections.ConnectionHandler;

public class NettyChannelInitializer extends ChannelInitializer<SocketChannel> {

    private final NettyServer nettyServer;

    public NettyChannelInitializer(NettyServer nettyServer) {
        this.nettyServer = nettyServer;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();
        pipeline.addLast("gameEncoder", new NetworkEncoder());
        pipeline.addLast("gameDecoder", new NetworkDecoder());
        pipeline.addLast("handler", new ConnectionHandler(this.nettyServer));
    }
}
