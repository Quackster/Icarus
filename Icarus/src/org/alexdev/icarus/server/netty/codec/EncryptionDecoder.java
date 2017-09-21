package org.alexdev.icarus.server.netty.codec;

import org.alexdev.icarus.encryption.RC4;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

public class EncryptionDecoder extends FrameDecoder {

    private RC4 rc4;
    
    public EncryptionDecoder(RC4 object) {
       this.rc4 = object;
    }

    @Override
    protected ChannelBuffer decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) {
        return rc4.decipher(buffer);
    }
}