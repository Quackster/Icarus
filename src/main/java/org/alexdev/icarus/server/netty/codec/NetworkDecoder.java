package org.alexdev.icarus.server.netty.codec;

import org.alexdev.icarus.server.netty.streams.NettyRequest;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

public class NetworkDecoder extends FrameDecoder {

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

            buffer.markReaderIndex();
            int length = buffer.readInt();

            if (buffer.readableBytes() < length) {
                buffer.resetReaderIndex();
                return null;
            }

            if (length < 0) {
                return null;
            }

            return new NettyRequest(length, buffer.readBytes(length));
        }
        
        return null;
    }
}