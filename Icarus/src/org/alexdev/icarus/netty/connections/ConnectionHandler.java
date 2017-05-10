package org.alexdev.icarus.netty.connections;

import org.alexdev.icarus.Icarus;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.game.player.PlayerManager;
import org.alexdev.icarus.log.Log;
import org.alexdev.icarus.netty.readers.NettyRequest;
import org.alexdev.icarus.util.Util;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.channel.ChannelStateEvent;
import org.jboss.netty.channel.ExceptionEvent;
import org.jboss.netty.channel.MessageEvent;
import org.jboss.netty.channel.SimpleChannelHandler;

public class ConnectionHandler extends SimpleChannelHandler {
	
    private SessionManager sessionManager;

    public ConnectionHandler(SessionManager sessionManager) {
        this.sessionManager = sessionManager;
    }

    @Override
    public void channelOpen(ChannelHandlerContext ctx, ChannelStateEvent e) {

        sessionManager.addSession(ctx.getChannel());
        
        Player player = (Player) ctx.getChannel().getAttachment();
        
        if (Util.getConfiguration().get("Logging", "log.connections", Boolean.class)) {
            Log.println("[" + player.getNetwork().getConnectionId() + "] Connection from " + ctx.getChannel().getRemoteAddress().toString().replace("/", "").split(":")[0]);
        }

    } 

    @Override
    public void channelClosed(ChannelHandlerContext ctx, ChannelStateEvent e) {
        
        sessionManager.removeSession(ctx.getChannel());
        
        Player player = (Player) ctx.getChannel().getAttachment();
        
        if (Util.getConfiguration().get("Logging", "log.connections", Boolean.class)) {
            Log.println("[" + player.getNetwork().getConnectionId() + "] Disconnection from " + ctx.getChannel().getRemoteAddress().toString().replace("/", "").split(":")[0]);
        }
        
        player.dispose();
        
    }

    @Override
    public void messageReceived(ChannelHandlerContext ctx, MessageEvent e) {

        try {

            Player player = (Player) ctx.getChannel().getAttachment();
            NettyRequest request = (NettyRequest) e.getMessage();
            
            if (request == null) {
                return;
            }

            if (Util.getConfiguration().get("Logging", "log.packets", Boolean.class)) {
                Log.println("Received: " + request.getMessageId() + " / " + request.getMessageBody());
            }

            if (player != null){
                Icarus.getServer().getMessageHandler().handleRequest(player, request);
            }

        } catch (Exception ex) {
            Log.exception(ex);
        }
    }

}
