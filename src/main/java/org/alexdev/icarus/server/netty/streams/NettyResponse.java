package org.alexdev.icarus.server.netty.streams;

import java.io.IOException;
import java.nio.charset.Charset;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import org.alexdev.icarus.server.api.messages.Response;
import org.alexdev.icarus.server.api.messages.Serialisable;

public class NettyResponse implements Response {

    private short id;
    private ByteBuf buffer;

    private boolean finalised;

    /* (non-Javadoc)
     * @see org.alexdev.icarus.server.api.messages.Response#init(int)
     */
    @Override
    public void init(short id) {
        this.id = id;

        this.buffer = Unpooled.buffer(6);
        this.buffer.writeInt(0);
        this.buffer.writeShort(id);
    }
    
    /* (non-Javadoc)
     * @see org.alexdev.icarus.server.api.messages.Response#writeString(java.lang.Object)
     */
    @Override
    public void writeString(Object obj) {
        buffer.writeShort(obj.toString().length());
        buffer.writeBytes(obj.toString().getBytes());
    }

    /* (non-Javadoc)
     * @see org.alexdev.icarus.server.api.messages.Response#writeInt(java.lang.Integer)
     */
    @Override
    public void writeInt(Integer obj) {
       buffer.writeInt(obj);
    }

    /* (non-Javadoc)
     * @see org.alexdev.icarus.server.api.messages.Response#writeInt(java.lang.Boolean)
     */
    @Override
    public void writeInt(Boolean obj) {
        buffer.writeInt(obj ? 1 : 0);
    }

    /* (non-Javadoc)
     * @see org.alexdev.icarus.server.api.messages.Response#writeShort(int)
     */
    @Override
    public void writeShort(int obj) {
        buffer.writeShort((short)obj);
    }

    /* (non-Javadoc)
     * @see org.alexdev.icarus.server.api.messages.Response#writeBool(java.lang.Boolean)
     */
    @Override
    public void writeBool(Boolean obj) {
        buffer.writeBoolean(obj);
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
    public ByteBuf get() {

        if (!this.finalised) {
            this.buffer.setInt(0, this.buffer.writerIndex() - 4);
            this.finalised = true;
        }
        
        return this.buffer;
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
}
