package org.alexdev.icarus.server.api.messages;

public interface Response {

    /**
     * Initiates the response.
     *
     * @param id the id
     */
    public void init(int id);
    
    /**
     * Write string.
     *
     * @param obj the obj
     */
    public void writeString(Object obj);
    
    /**
     * Write int.
     *
     * @param obj the obj
     */
    public void writeInt(Integer obj);
    
    /**
     * Write int.
     *
     * @param obj the obj
     */
    public void writeInt(Boolean obj);
    
    /**
     * Write short.
     *
     * @param obj the obj
     */
    public void writeShort(int obj);
    
    /**
     * Write bool.
     *
     * @param obj the obj
     */
    public void writeBool(Boolean obj);
    
    /**
     * Gets the body string.
     *
     * @return the body string
     */
    public String getBodyString();
    
    /**
     * Gets the.
     *
     * @return the object
     */
    public Object get();
    
    /**
     * Gets the header.
     *
     * @return the header
     */
    public int getHeader();
    
    /**
     * Checks if is finalised.
     *
     * @return true, if is finalised
     */
    public boolean isFinalised();

}
