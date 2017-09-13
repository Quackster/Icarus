package org.alexdev.icarus.server.netty.streams;

import java.nio.charset.Charset;
import org.alexdev.icarus.server.api.messages.ClientMessage;
import org.jboss.netty.buffer.ChannelBuffer;
import org.jboss.netty.buffer.ChannelBuffers;

public class NettyRequest implements ClientMessage {

    private int header;
    public ChannelBuffer buffer;
    
    public NettyRequest(int messageId, ChannelBuffer buffer) {
        this.header = (short) messageId;
        this.buffer = (buffer == null || buffer.readableBytes() == 0) ? ChannelBuffers.EMPTY_BUFFER : buffer;
    }

    public int readShort() {
        return buffer.readShort();
    }

    public Integer readInt() {
        try {
            return buffer.readInt();
        } catch (Exception e) {
            return 0;
        }
    }
    

    public boolean readIntAsBool() {
        try {
            return buffer.readInt() == 1;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean readBoolean()  {
        try {
            return buffer.readByte() == 1;
        }
        catch (Exception e)    {
            return false;
        }
    }

    public String readString() {
        
        try {
            int length = this.readShort();
            byte[] data = this.buffer.readBytes(length).array();

            return new String(data);
        } catch (Exception e) {
            return null;
        }
    }

    public byte[] readBytes(int len) {
        
        try {
            return this.buffer.readBytes(len).array();
        } catch (Exception e) {
            return null;
        }
    }

    public String getMessageBody() {
        
        String consoleText = new String(buffer.toString(Charset.defaultCharset()));

        for (int i = 0; i < 13; i++) { 
            consoleText = consoleText.replace(Character.toString((char)i), "[" + i + "]");
        }

        return consoleText;
    }
    
    public ChannelBuffer getBuffer() {
        return buffer;
    }

    public int getMessageId() {
        return header;
    }
}
