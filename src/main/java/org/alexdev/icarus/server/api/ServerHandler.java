package org.alexdev.icarus.server.api;

public abstract class ServerHandler {

    private Integer port;
    private String ip;

    public ServerHandler(String ip, Integer port) {
        this.port = port;
        this.ip = ip;
    }

    /**
     * Create socket.
     *
     * @return true, if successful
     */
    public abstract void createSocket();

    /**
     * Bind socket.
     *
     * @return true, if successful
     */
    public abstract boolean bind();

    /**
     * Gets the port.
     *
     * @return the port
     */
    public Integer getPort() {
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
    public void setPort(Integer port) {
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