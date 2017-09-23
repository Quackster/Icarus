package org.alexdev.icarus.server.netty.codec;

import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

public class PolicyDecoder extends FrameDecoder {

    @Override
    protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) {

        buffer.markReaderIndex();
        
        if (buffer.readableBytes() < 6) {
            return null;
        }

        byte delimiter = buffer.readByte();
        buffer.resetReaderIndex();

        if (delimiter == 60) {
            channel.write("<?xml version=\"1.0\"?>\r\n"
                    + "<!DOCTYPE cross-domain-policy SYSTEM \"/xml/dtds/cross-domain-policy.dtd\">\r\n"
                    + "<cross-domain-policy>\r\n"
                    + "<allow-access-from domain=\"*\" to-ports=\"*\" />\r\n"
                    + "</cross-domain-policy>\0");
        } else {
            
            channel.getPipeline().remove(this);
            
            NetworkDecoder decoder = (NetworkDecoder) channel.getPipeline().get("gameDecoder");
            return decoder.decode(ctx, channel, buffer);
        }
        
        return null;
    }
}