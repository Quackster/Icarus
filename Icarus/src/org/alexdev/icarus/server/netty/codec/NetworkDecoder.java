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

import java.nio.ByteBuffer;

import org.alexdev.icarus.server.netty.streams.NettyRequest;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.channel.Channel;
import org.jboss.netty.channel.ChannelHandlerContext;
import org.jboss.netty.handler.codec.frame.FrameDecoder;

public class NetworkDecoder extends FrameDecoder {
    
	@Override
	protected Object decode(ChannelHandlerContext ctx, Channel channel, ChannelBuffer buffer) {
		
		try  {		
			
			if (buffer.readableBytes() < 6) {
				channel.close();
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
			
		} catch (Exception e){
			// Ignore
		}
		
		return null;
	}
}