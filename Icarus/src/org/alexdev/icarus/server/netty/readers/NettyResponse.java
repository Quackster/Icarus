package org.alexdev.icarus.server.netty.readers;

import java.io.IOException;
import java.nio.charset.Charset;

import org.alexdev.icarus.log.Log;
import org.alexdev.icarus.server.api.messages.AbstractResponse;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBufferOutputStream;
import org.jboss.netty.buffer.ChannelBuffers;

public class NettyResponse implements AbstractResponse
{

	private int id;
	private boolean finalised;
	private ChannelBufferOutputStream bodystream;
	private ChannelBuffer body;

	public NettyResponse() {
		this.loadArray();
	}
	
	public NettyResponse(int id) {
		this.init(id);
	}

	@Override
	public void init(int id) {

		this.id = id;
		this.finalised = false;
		this.body = ChannelBuffers.dynamicBuffer();
		this.bodystream = new ChannelBufferOutputStream(body);
		
		try {
			this.bodystream.writeInt(0);
			this.bodystream.writeShort(id);

		} catch (Exception e) {
			Log.exception(e);
		}
	}
	
	public void loadArray() {
		this.id = -1;
		this.finalised = false;
		this.body = ChannelBuffers.dynamicBuffer();
		this.bodystream = new ChannelBufferOutputStream(body);
	}

	@Override
	public void writeString(Object obj) {

		if (obj == null) {
			obj = "";
		}
		
		try {
			bodystream.writeUTF(obj.toString());
		} catch (IOException e) {
			Log.exception(e);
		}
	}

	@Override
	public void writeInt(Integer obj) {
		try {
			bodystream.writeInt(obj);
		} catch (IOException e) {
			Log.exception(e);
		}
	}

	@Override
	public void appendInt32(Boolean obj) {
		try {
			bodystream.writeInt(obj ? 1 : 0);
		} catch (IOException e) {
			Log.exception(e);
		}
	}

	@Override
	public void appendShort(int obj) {
		try {
			bodystream.writeShort((short)obj);
		} catch (IOException e) {
			Log.exception(e);
		}
	}

	@Override
	public void writeBool(Boolean obj) {
		try {
			bodystream.writeBoolean(obj);
		} catch (IOException e) {
			Log.exception(e);
		}
	}

	public String getBodyString() {
		
		String str = new String(this.get().toString(Charset.defaultCharset()));
		
		for (int i = 0; i < 14; i++) { 
			str = str.replace(Character.toString((char)i), "[" + i + "]");
		}

		return str;
	}
	
	public ChannelBuffer get() {

		if (!this.finalised) {
			this.body.setInt(0, this.body.writerIndex() - 4);
			this.finalised = true;
		}
		
		return this.body;
	}

	public int getHeader() {
		return this.id;
	}	
}
