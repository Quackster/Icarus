package org.alexdev.icarus.server.api;

import org.alexdev.icarus.messages.MessageHandler;

public abstract class IServerHandler {
    
    private int port;
    private String ip;
    private MessageHandler messages;
    
    /**
     * Instantiates a new i server handler.
     */
    public IServerHandler() {
        this.messages = new MessageHandler();
    }
    
    /**
     * Listen socket.
     *
     * @return true, if successful
     */
    public abstract boolean listenSocket();

    /**
     * Gets the port.
     *
     * @return the port
     */
    public int getPort() {
        return port;
    }

    /**
     * Gets the ip.
     *
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * Sets the port.
     *
     * @param port the new port
     */
    public void setPort(int port) {
        this.port = port;
    }

    /**
     * Sets the ip.
     *
     * @param ip the new ip
     */
    public void setIp(String ip) {
        this.ip = ip;
    }

    /**
     * Gets the message handler.
     *
     * @return the message handler
     */
    public MessageHandler getMessageHandler() {
        return messages;
    }
    
}
