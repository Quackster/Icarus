package org.alexdev.icarus.web.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class NettyWebServer {

    private int port = 80;

    private final EventLoopGroup masterGroup;
    private final EventLoopGroup slaveGroup;

    private ChannelFuture channel;
    private ServerBootstrap bootstrap;

    public NettyWebServer(int port) {
        this.port = port;
        this.masterGroup = new NioEventLoopGroup();
        this.slaveGroup = new NioEventLoopGroup();
    }

    public void start() {
        try {

            final ServerBootstrap bootstrap = new ServerBootstrap()
                .group(masterGroup, slaveGroup)
                .channel(NioServerSocketChannel.class)
                .childHandler(new NettyChannelInitializer())
                .option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true);

            channel = bootstrap.bind(this.port).sync();

        } catch (final InterruptedException e) {
            e.printStackTrace();
        }
    }
}