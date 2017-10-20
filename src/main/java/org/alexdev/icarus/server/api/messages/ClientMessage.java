package org.alexdev.icarus.server.api.messages;

public interface ClientMessage {

    /**
     * Read integer.
     *
     * @return the integer
     */
    public Integer readInt();
    
    /**
     * Read integer as boolean.
     *
     * @return true, if successful
     */
    public boolean readIntAsBool();
    
    /**
     * Read boolean.
     *
     * @return true, if successful
     */
    public boolean readBoolean();
    
    /**
     * Read string.
     *
     * @return the string
     */
    public String readString();
    
    /**
     * Read bytes.
     *
     * @param len the len
     * @return the byte[]
     */
    public byte[] readBytes(int len);
    
    /**
     * Gets the message body.
     *
     * @return the message body
     */
    public String getMessageBody();
    
    /**
     * Gets the message id.
     *
     * @return the message id
     */
    public short getMessageId();

    /**
     * Returns the length of the received packet
     * @return the length
     */
    public int getLength();
}