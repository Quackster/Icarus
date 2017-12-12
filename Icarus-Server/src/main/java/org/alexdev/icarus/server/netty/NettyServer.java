package org.alexdev.icarus.server.netty;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.buffer.UnpooledByteBufAllocator;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.FixedRecvByteBufAllocator;
import io.netty.channel.epoll.Epoll;
import io.netty.channel.epoll.EpollEventLoopGroup;
import io.netty.channel.epoll.EpollServerSocketChannel;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.util.concurrent.GlobalEventExecutor;
import org.alexdev.icarus.log.Log;
import org.alexdev.icarus.server.api.ServerHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;

public class NettyServer extends ServerHandler  {

    final private static int BACK_LOG = 20;
    final private static int BUFFER_SIZE = 2048;
	final private static Logger log = LoggerFactory.getLogger(NettyServer.class);

    private DefaultChannelGroup channels;
    private ServerBootstrap bootstrap;

    public NettyServer(String ip, Integer port) {
        super(ip, port);
        this.channels = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);
        this.bootstrap = new ServerBootstrap();
    }

    @Override
    public void createSocket() {
        int threads = Runtime.getRuntime().availableProcessors() * 2;
        EventLoopGroup bossGroup = (Epoll.isAvailable()) ? new EpollEventLoopGroup(threads) : new NioEventLoopGroup(threads);
        EventLoopGroup workerGroup = (Epoll.isAvailable()) ? new EpollEventLoopGroup() : new NioEventLoopGroup();

        this.bootstrap.group(bossGroup, workerGroup)
            .channel((Epoll.isAvailable()) ? EpollServerSocketChannel.class : NioServerSocketChannel.class)
            .childHandler(new NettyChannelInitializer(this))
            .option(ChannelOption.SO_BACKLOG, BACK_LOG)
            .childOption(ChannelOption.TCP_NODELAY, true)
            .childOption(ChannelOption.SO_KEEPALIVE, true)
            .childOption(ChannelOption.SO_RCVBUF, BUFFER_SIZE)
            .childOption(ChannelOption.RCVBUF_ALLOCATOR, new FixedRecvByteBufAllocator(BUFFER_SIZE))
            .childOption(ChannelOption.ALLOCATOR, new UnpooledByteBufAllocator(false));
    }

    @Override
    public boolean bind() {
        this.bootstrap.bind(new InetSocketAddress(this.getIp(), this.getPort())).addListener(objectFuture -> {
            if (!objectFuture.isSuccess()) {
                Log.getErrorLogger().error("Failed to start server on address: {}:{}", this.getIp(), this.getPort());
                Log.getErrorLogger().error("Please double check there's no programs using the same port, and you have set the correct IP address to listen on.", this.getIp(), this.getPort());
            } else {
                log.info("Server is listening on {}:{}", this.getIp(), this.getPort());
                log.info("Ready for connections!");
            }
        });

        return false;
    }

    /**
     * Get default channel group of channels
     * @return channels
     */
    public DefaultChannelGroup getChannels() {
        return channels;
    }
}
