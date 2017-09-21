package org.alexdev.icarus.server.netty.codec;

import org.alexdev.icarus.encryption.RC4;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

public class EncryptionDecoder extends FrameDecoder {

    private RC4 rc4;

    public EncryptionDecoder(RC4 rc4) {
        this.rc4 = rc4;
    }

    @Override
    protected ChannelBuffer decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) {

        byte[] message = new byte[buffer.readableBytes()];
        buffer.readBytes(message);

        ChannelBuffer result = ChannelBuffers.dynamicBuffer();
        result.writeBytes(this.rc4.decipher(message));

        /*ChannelBuffer result = ChannelBuffers.dynamicBuffer();

        while (buffer.readableBytes() > 0) {
            result.writeByte((byte) (buffer.readByte() ^ this.rc4.next()));
        }*/

        return result;
    }
}