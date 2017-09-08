package org.alexdev.icarus.server.netty.streams;

import java.io.IOException;
import java.nio.charset.Charset;

import org.alexdev.icarus.log.Log;
import org.alexdev.icarus.server.api.messages.Response;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBufferOutputStream;
import org.jboss.netty.buffer.ChannelBuffers;

public class NettyResponse implements Response {
    
    private boolean finalised;
    private int id;
    private ChannelBufferOutputStream bodystream;
    private ChannelBuffer body;

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
    
    @Override
    public void writeString(Object obj) {

        if (obj == null) {
            obj = "";
        }
        
        try {
            bodystream.writeShort(obj.toString().length());
            bodystream.write(obj.toString().getBytes());
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
    public void writeInt(Boolean obj) {
        try {
            bodystream.writeInt(obj ? 1 : 0);
        } catch (IOException e) {
            Log.exception(e);
        }
    }

    @Override
    public void writeShort(int obj) {
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

    @Override
    public boolean isFinalised() {
        return this.finalised;
    }

    public void writeDouble(int i) {
        try {
            bodystream.writeDouble(i);
        } catch (IOException e) {
            Log.exception(e);
        }
    }    
}
