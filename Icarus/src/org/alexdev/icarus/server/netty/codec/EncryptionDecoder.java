package org.alexdev.icarus.server.netty.codec;

import java.nio.ByteBuffer;

import org.alexdev.icarus.encryption.RC4;
import org.alexdev.icarus.game.player.Player;
import org.alexdev.icarus.log.Log;
import org.alexdev.icarus.server.netty.streams.NettyRequest;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandler;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

public class EncryptionDecoder extends FrameDecoder {

    private RC4 rc4;


    public EncryptionDecoder(RC4 object) {
       this.rc4 = object;
    }
    
    
    @Override
    protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) {
        Log.println("called!");
        return rc4.decipher(buffer);
    }
}