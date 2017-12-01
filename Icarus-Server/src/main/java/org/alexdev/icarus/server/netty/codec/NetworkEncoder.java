package org.alexdev.icarus.server.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.netty.streams.NettyResponse;
import org.alexdev.icarus.util.config.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class NetworkEncoder extends MessageToMessageEncoder<MessageComposer> {

    final private static Logger log = LoggerFactory.getLogger(NetworkEncoder.class);

    @Override
    protected void encode(ChannelHandlerContext ctx, MessageComposer msg, List<Object> out) throws Exception {
        ByteBuf buffer = Unpooled.buffer();

        NettyResponse response = new NettyResponse(msg.getHeader(), buffer);
        msg.compose(response);

        if (!response.hasLength()) {
            buffer.setInt(0, buffer.writerIndex() - 4);
        }

        if (Configuration.getInstance().getServerConfig().get("Logging", "log.sent.packets", Boolean.class)) {
            log.info("SENT: {} / {}", msg.getHeader(), response.getBodyString());
        }

        out.add(buffer);
    }
}