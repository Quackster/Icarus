package org.alexdev.icarus.server.netty.connections;

import org.alexdev.icarus.game.player.Player;

import org.alexdev.icarus.messages.MessageHandler;
import org.alexdev.icarus.server.netty.NettyPlayerNetwork;
import org.alexdev.icarus.server.netty.streams.NettyRequest;
import org.alexdev.icarus.util.Util;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ConnectionHandler extends SimpleChannelHandler {
	
	final private static Logger log = LoggerFactory.getLogger(ConnectionHandler.class);
	
    /* (non-Javadoc)
     * @see org.jboss.netty.channel.SimpleChannelHandler#channelOpen(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.ChannelStateEvent)
     */
    @Override
    public void channelOpen(ChannelHandlerContext ctx, ChannelStateEvent e) {

        Channel channel = ctx.getChannel();

        Player player = new Player(new NettyPlayerNetwork(channel, channel.getId()));
        channel.setAttachment(player);

        if (Util.getConfiguration().get("Logging", "log.connections", Boolean.class)) {
            log.info("[{}] Connection from {} ", player.getNetwork().getConnectionId(), channel.getRemoteAddress().toString().replace("/", "").split(":")[0]);
        }

    } 

    /* (non-Javadoc)
     * @see org.jboss.netty.channel.SimpleChannelHandler#channelClosed(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.ChannelStateEvent)
     */
    @Override
    public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) {
        
        Player player = (Player) ctx.getChannel().getAttachment();

        if (Util.getConfiguration().get("Logging", "log.connections", Boolean.class)) {
        	log.info("[{}] Disonnection from {} ", player.getNetwork().getConnectionId(), ctx.getChannel().getRemoteAddress().toString().replace("/", "").split(":")[0]);
        }

        player.dispose();

    }

    /* (non-Javadoc)
     * @see org.jboss.netty.channel.SimpleChannelHandler#messageReceived(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.MessageEvent)
     */
    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {

        try {

            Player player = (Player) ctx.getChannel().getAttachment();
            NettyRequest request = (NettyRequest) e.getMessage();

            if (request == null) {
                return;
            }

            if (player != null){
                MessageHandler.handleRequest(player, request);
            }

        } catch (Exception ex) {
            log.error("Could not handle message: ", ex);
        }
    }

    /* (non-Javadoc)
     * @see org.jboss.netty.channel.SimpleChannelHandler#exceptionCaught(org.jboss.netty.channel.ChannelHandlerContext, org.jboss.netty.channel.ExceptionEvent)
     */
    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, ExceptionEvent e) {
    	log.error("Netty error occurred: ", e.getCause());
    }

}
