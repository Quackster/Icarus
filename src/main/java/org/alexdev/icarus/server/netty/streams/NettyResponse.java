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

    public NettyResponse(short header, ByteBuf out) {
        this.id = header;
        this.buffer = out;
        this.buffer.writeInt(-1);
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
        
        String str = new String(this.buffer.toString(Charset.defaultCharset()));
        
        for (int i = 0; i < 14; i++) { 
            str = str.replace(Character.toString((char)i), "[" + i + "]");
        }

        return str;
    }

    /**
     * Gets has the length been set
     *
     * @return true, if the length was set
     */
    public boolean hasLength() {
        return (this.buffer.getInt(0) > -1);

    }

    /* (non-Javadoc)
     * @see org.alexdev.icarus.server.api.messages.Response#getHeader()
     */
    public int getHeader() {
        return this.id;
    }

    /* (non-Javadoc)
     * @see org.alexdev.icarus.server.api.messages.Response#getContent()
     */
    @Override
    public ByteBuf getContent() {
        return this.buffer;
    }
}
