package org.alexdev.icarus.server.netty.streams;

import java.nio.charset.Charset;

import org.alexdev.icarus.server.api.messages.ClientMessage;
import org.jboss.netty.buffer.ChannelBuffer;

public class NettyRequest implements ClientMessage {

    final private short header;
    final private int length;
    final public ChannelBuffer buffer;

    public NettyRequest(int length, ChannelBuffer buffer) {
        this.buffer = buffer;
        this.header = buffer.readShort();
        this.length = length;
    }
    
    /* (non-Javadoc)
     * @see org.alexdev.icarus.server.api.messages.ClientMessage#readInt()
     */
    public Integer readInt() {
        
        try {
            return this.buffer.readInt();
        } catch (Exception e) {
            return 0;
        }
    }
    
    /* (non-Javadoc)
     * @see org.alexdev.icarus.server.api.messages.ClientMessage#readIntAsBool()
     */
    public boolean readIntAsBool() {
        
        try {
            return this.buffer.readInt() == 1;
        } catch (Exception e) {
            return false;
        }
    }

    /* (non-Javadoc)
     * @see org.alexdev.icarus.server.api.messages.ClientMessage#readBoolean()
     */
    public boolean readBoolean()  {
        
        try {
            return this.buffer.readByte() == 1;
        } catch (Exception e)    {
            return false;
        }
    }

    /* (non-Javadoc)
     * @see org.alexdev.icarus.server.api.messages.ClientMessage#readString()
     */
    public String readString() {
        
        try {
            int length = this.buffer.readShort();
            byte[] data = this.buffer.readBytes(length).array();

            return new String(data);
        } catch (Exception e) {
            return null;
        }
    }

    /* (non-Javadoc)
     * @see org.alexdev.icarus.server.api.messages.ClientMessage#readBytes(int)
     */
    public byte[] readBytes(int len) {
        
        try {
            return this.buffer.readBytes(len).array();
        } catch (Exception e) {
            return null;
        }
    }

    /* (non-Javadoc)
     * @see org.alexdev.icarus.server.api.messages.ClientMessage#getMessageBody()
     */
    public String getMessageBody() {
        
        String consoleText = new String(buffer.toString(Charset.defaultCharset()));

        for (int i = 0; i < 13; i++) { 
            consoleText = consoleText.replace(Character.toString((char)i), "[" + i + "]");
        }

        return consoleText;
    }
    
    /**
     * Gets the buffer.
     *
     * @return the buffer
     */
    public ChannelBuffer getBuffer() {
        return buffer;
    }

    /* (non-Javadoc)
     * @see org.alexdev.icarus.server.api.messages.ClientMessage#getMessageId()
     */
    public short getMessageId() {
        return header;
    }

    /* (non-Javadoc)
     * @see org.alexdev.icarus.server.api.messages.ClientMessage#getLength()
     */
    public int getLength() {
        return length;
    }
}
