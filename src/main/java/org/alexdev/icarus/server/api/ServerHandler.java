package org.alexdev.icarus.server.api;

public abstract class ServerHandler {
    
    private int port;
    private String ip;
   
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
    
}
