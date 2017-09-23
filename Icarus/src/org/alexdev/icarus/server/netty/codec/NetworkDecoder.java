package org.alexdev.icarus.server.netty.codec;

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

        buffer.markReaderIndex();
        int length = buffer.readInt();

        if (buffer.readableBytes() < length) {
            buffer.resetReaderIndex();
            return null;
        }

        if (length < 0) {
            return null;
        }
        
        return new NettyRequest(buffer.readBytes(length));
    }
}