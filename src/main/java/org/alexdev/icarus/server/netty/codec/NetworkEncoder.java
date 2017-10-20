/*
 * Copyright (c) 2012 Quackster <alex.daniel.97@gmail>. 
 * 
 * This file is part of Sierra.
 * 
 * Sierra is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * Sierra is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with Sierra.  If not, see <http ://www.gnu.org/licenses/>.
 */

package org.alexdev.icarus.server.netty.codec;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.server.netty.streams.NettyResponse;
import org.alexdev.icarus.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class NetworkEncoder extends MessageToByteEncoder<MessageComposer> {

    final private static Logger log = LoggerFactory.getLogger(NetworkEncoder.class);

    @Override
    protected void encode(ChannelHandlerContext ctx, MessageComposer msg, ByteBuf out) throws Exception {

        NettyResponse response = new NettyResponse(msg.getHeader(), out);
        msg.compose(response);

        if (!response.hasLength()) {
            response.getContent().setInt(0, response.getContent().writerIndex() - 4);
        }

        if (Util.getServerConfig().get("Logging", "log.sent.packets", Boolean.class)) {
            log.info("SENT: {} / {}", msg.getHeader(), response.getBodyString());
        }
    }
}
