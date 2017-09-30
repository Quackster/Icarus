package org.alexdev.icarus.server.netty.streams;

import java.nio.charset.Charset;
import java.util.Arrays;

import org.alexdev.icarus.server.api.messages.ClientMessage;
import org.jboss.netty.buffer.ChannelBuffer;

public class NettyRequest implements ClientMessage {

    private short header;
    public ChannelBuffer buffer;
    private int length;
    
    public NettyRequest(int length, ChannelBuffer buffer) {
        this.buffer = buffer;
        this.header = buffer.readShort();
        this.length = length;
    }
    
    public Integer readInt() {
        
        try {
            return this.buffer.readInt();
        } catch (Exception e) {
            return 0;
        }
    }
    
    public boolean readIntAsBool() {
        
        try {
            return this.buffer.readInt() == 1;
        } catch (Exception e) {
            return false;
        }
    }

    public boolean readBoolean()  {
        
        try {
            return this.buffer.readByte() == 1;
        } catch (Exception e)    {
            return false;
        }
    }

    public String readString() {
        
        try {
            int length = this.buffer.readShort();
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

    public byte[] getRawMessage() {
        
        byte[] complete = this.buffer.array();
        return Arrays.copyOfRange(complete, 6, complete.length); 
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

    public short getMessageId() {
        return header;
    }

    public int getLength() {
        return length;
    }
}
