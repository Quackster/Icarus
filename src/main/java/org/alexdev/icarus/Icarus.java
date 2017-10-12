package org.alexdev.icarus;

import java.net.InetAddress;

import org.alexdev.icarus.dao.mysql.Dao;
import org.alexdev.icarus.game.GameScheduler;
import org.alexdev.icarus.game.catalogue.CatalogueManager;
import org.alexdev.icarus.game.commands.CommandManager;
import org.alexdev.icarus.game.groups.GroupManager;
import org.alexdev.icarus.game.item.ItemManager;
import org.alexdev.icarus.game.navigator.NavigatorManager;
import org.alexdev.icarus.game.pets.PetManager;
import org.alexdev.icarus.game.plugins.PluginManager;
import org.alexdev.icarus.game.room.RoomManager;
import org.alexdev.icarus.messages.MessageHandler;
import org.alexdev.icarus.server.api.ServerHandler;
import org.alexdev.icarus.util.Util;
import org.alexdev.icarus.util.config.Configuration;
import org.alexdev.icarus.util.metadata.Metadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Icarus extends Metadata {

    private static String serverIP;
    private static String rawConfigIP;
    
    private static int serverPort;
    private static long startupTime;
    
    private static ServerHandler server;
    
    private static Logger log = null;//LoggerFactory.getLogger(Icarus.class);

    /**
     * Main call of Java application
     * @param args System arguments
     */
    public static void main(String[] args) {

        startupTime = Util.getCurrentTimeSeconds();
        
        try {

        	Configuration.createConfig();
        	log = LoggerFactory.getLogger(Icarus.class);
            
            log.info("Icarus - Habbo Hotel PRODUCTION63 Server");
            log.info("Loading server...");

            rawConfigIP = Util.getConfiguration().get("Server", "server.ip", String.class);
            serverIP = rawConfigIP;
            
            if (!isValidIpAddress(rawConfigIP)) {
                serverIP = InetAddress.getByName(rawConfigIP).getHostAddress();
            }

            serverPort = Util.getConfiguration().get("Server", "server.port", int.class);
            
            if (!Dao.connect()) {
                return;
            }
            
            server = Class.forName(Icarus.getServerClassPath()).asSubclass(ServerHandler.class).getDeclaredConstructor().newInstance();
            server.setIp(serverIP);
            server.setPort(serverPort);

            log.info("Setting up game");

            RoomManager.load();
            NavigatorManager.load();
            ItemManager.load();
            CatalogueManager.load();
            GameScheduler.load();
            PetManager.load();
            GroupManager.load();
            CommandManager.load();
            PluginManager.load();
            MessageHandler.load();
 
            log.info("");
            log.info("Settting up server");
            
            if (server.listenSocket()) {
                log.info("Server is listening on " + serverIP + ":" + serverPort);
            } else {
                log.error("Server could not listen on " + serverPort + ":" + serverPort + ", please double check everything is correct in icarus.properties");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Validate IP address that the server attempts to listen to
     * 
     * @param ip the ipv4
     * @return true if valid IPv4
     */
    public static boolean isValidIpAddress(String ip) {
        try {
            String PATTERN = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";
            return ip.matches(PATTERN);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Returns the path to the class it will attempt to resolve and use
     * 
     * @return java class path string
     */
    private static String getServerClassPath() {
        return "org.alexdev.icarus.server.netty.NettyServer";
    }

    /**
     * Returns the interface to the server handler
     * 
     * @return {@link ServerHandler} interface
     */
    public static ServerHandler getServer() {
        return server;
    }

    /**
     * Gets the server IPv4 IP address it is currently (or attempting to) listen on
     * @return IP as string
     */
    public static String getServerIP() {
        return serverIP;
    }

    /**
     * Gets the server port it is currently (or attempting to) listen on
     * @return string of IP
     */
    public static int getServerPort() {
        return serverPort;
    }

    /**
     * Gets the startup time.
     *
     * @return the startup time
     */
    public static long getStartupTime() {
        return startupTime;
    }
    
    /**
     * 
     */
    public static Logger getLodgger() {
    	return log;
    }
   
}