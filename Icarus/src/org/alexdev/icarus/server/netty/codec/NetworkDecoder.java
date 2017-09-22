package org.alexdev.icarus.server.netty.codec;

import java.nio.ByteBuffer;

import org.alexdev.icarus.server.netty.streams.NettyRequest;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

public class NetworkDecoder extends FrameDecoder {

    @Override
    protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) {

        if (buffer.readableBytes() < 6) {
            return null;
        }

        byte[] length = buffer.readBytes(4).array();

        if (length[0] == 60) {
            
            buffer.discardReadBytes();
            channel.write("<?xml version=\"1.0\"?>\r\n"
                    + "<!DOCTYPE cross-domain-policy SYSTEM \"/xml/dtds/cross-domain-policy.dtd\">\r\n"
                    + "<cross-domain-policy>\r\n"
                    + "<allow-access-from domain=\"*\" to-ports=\"*\" />\r\n"
                    + "</cross-domain-policy>\0");

        } else {

            int messageLength = ByteBuffer.wrap(length).asIntBuffer().get();
            ChannelBuffer messageBuffer = buffer.readBytes(messageLength);
            Short header = messageBuffer.readShort();
            return new NettyRequest(header, messageBuffer);
        }    

        return null;
    }
}