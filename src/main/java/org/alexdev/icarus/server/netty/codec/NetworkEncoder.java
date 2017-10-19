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
import io.netty.handler.codec.MessageToMessageEncoder;
import org.alexdev.icarus.messages.types.MessageComposer;
import org.alexdev.icarus.util.Util;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class NetworkEncoder extends MessageToMessageEncoder<MessageComposer> {

    final private static Logger log = LoggerFactory.getLogger(NetworkEncoder.class);

    @Override
    protected void encode(ChannelHandlerContext ctx, MessageComposer msg, List<Object> out) throws Exception {

        if (!msg.getResponse().isFinalised()) {
            msg.write();
        }

        if (Util.getServerConfig().get("Logging", "log.sent.packets", Boolean.class)) {
            log.info("SENT: {} / {}", msg.getResponse().getHeader(), msg.getResponse().getBodyString());
        }

        ByteBuf buffer = msg.getResponse().get();
        buffer.retain();
        out.add(buffer);
    }
}
