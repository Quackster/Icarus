package org.alexdev.icarus.server.netty.streams;

import java.io.IOException;
import java.nio.charset.Charset;

import org.alexdev.icarus.log.Log;
import org.alexdev.icarus.server.api.messages.Response;
import org.alexdev.icarus.server.api.messages.Serialisable;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBufferOutputStream;
import org.jboss.netty.buffer.ChannelBuffers;

public class NettyResponse implements Response {
    
    private boolean finalised;
    private short id;
    private ChannelBufferOutputStream bodystream;
    private ChannelBuffer body;

    /* (non-Javadoc)
     * @see org.alexdev.icarus.server.api.messages.Response#init(int)
     */
    @Override
    public void init(short id) {

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
    
    /* (non-Javadoc)
     * @see org.alexdev.icarus.server.api.messages.Response#writeString(java.lang.Object)
     */
    @Override
    public void writeString(Object obj) {

        try {
            
            bodystream.writeShort(obj.toString().length());
            bodystream.write(obj.toString().getBytes());
            
        } catch (IOException e) {
            Log.exception(e);
        }
    }

    /* (non-Javadoc)
     * @see org.alexdev.icarus.server.api.messages.Response#writeInt(java.lang.Integer)
     */
    @Override
    public void writeInt(Integer obj) {
        
        try {
            
            bodystream.writeInt(obj);
            
        } catch (IOException e) {
            Log.exception(e);
        }
    }

    /* (non-Javadoc)
     * @see org.alexdev.icarus.server.api.messages.Response#writeInt(java.lang.Boolean)
     */
    @Override
    public void writeInt(Boolean obj) {
        
        try {
            
            bodystream.writeInt(obj ? 1 : 0);
            
        } catch (IOException e) {
            Log.exception(e);
        }
    }

    /* (non-Javadoc)
     * @see org.alexdev.icarus.server.api.messages.Response#writeShort(int)
     */
    @Override
    public void writeShort(int obj) {
        
        try {
            
            bodystream.writeShort((short)obj);
            
        } catch (IOException e) {
            Log.exception(e);
        }
    }

    /* (non-Javadoc)
     * @see org.alexdev.icarus.server.api.messages.Response#writeBool(java.lang.Boolean)
     */
    @Override
    public void writeBool(Boolean obj) {
        
        try {
            bodystream.writeBoolean(obj);
        } catch (IOException e) {
            Log.exception(e);
        }
    }
    
    /* (non-Javadoc)
     * @see org.alexdev.icarus.server.api.messages.Response#writeObject(org.alexdev.icarus.server.api.messages.Serialisable)
     */
    @Override
    public void writeObject(Serialisable serialise) {
        serialise.compose(this);
    }    

    /* (non-Javadoc)
     * @see org.alexdev.icarus.server.api.messages.Response#getBodyString()
     */
    public String getBodyString() {
        
        String str = new String(this.get().toString(Charset.defaultCharset()));
        
        for (int i = 0; i < 14; i++) { 
            str = str.replace(Character.toString((char)i), "[" + i + "]");
        }

        return str;
    }
    
    /* (non-Javadoc)
     * @see org.alexdev.icarus.server.api.messages.Response#get()
     */
    public ChannelBuffer get() {

        if (!this.finalised) {
            this.body.setInt(0, this.body.writerIndex() - 4);
            this.finalised = true;
        }
        
        return this.body;
    }

    /* (non-Javadoc)
     * @see org.alexdev.icarus.server.api.messages.Response#getHeader()
     */
    public int getHeader() {
        return this.id;
    }

    /* (non-Javadoc)
     * @see org.alexdev.icarus.server.api.messages.Response#isFinalised()
     */
    @Override
    public boolean isFinalised() {
        return this.finalised;
    }

    /**
     * Write double.
     *
     * @param i the number
     */
    public void writeDouble(int i) {
        try {
            bodystream.writeDouble(i);
        } catch (IOException e) {
            Log.exception(e);
        }
    }
}
