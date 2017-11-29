package org.alexdev.icarus;

import org.alexdev.icarus.dao.mysql.Dao;
import org.alexdev.icarus.dao.mysql.room.RoomDao;
import org.alexdev.icarus.dao.mysql.site.SiteDao;
import org.alexdev.icarus.dao.mysql.site.SiteKey;
import org.alexdev.icarus.game.GameScheduler;
import org.alexdev.icarus.game.GameSettings;
import org.alexdev.icarus.game.catalogue.CatalogueManager;
import org.alexdev.icarus.game.commands.CommandManager;
import org.alexdev.icarus.game.groups.GroupManager;
import org.alexdev.icarus.game.item.ItemManager;
import org.alexdev.icarus.game.navigator.NavigatorManager;
import org.alexdev.icarus.game.pets.PetManager;
import org.alexdev.icarus.game.plugins.PluginManager;
import org.alexdev.icarus.game.room.RoomManager;
import org.alexdev.icarus.server.api.ServerHandler;
import org.alexdev.icarus.util.Util;
import org.alexdev.icarus.util.config.Configuration;
import org.alexdev.icarus.util.locale.Locale;
import org.alexdev.icarus.util.metadata.Metadata;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.Constructor;
import java.net.InetAddress;
import java.nio.file.Paths;

public class Icarus extends Metadata {

    private static long startupTime;

    private static String serverIP;
    private static int serverPort;
    
    private static ServerHandler server;
    private static Logger log;

    /**
     * Main call of Java application
     * @param args System arguments
     */
    public static void main(String[] args) {

        startupTime = Util.getCurrentTimeSeconds();

        try {
            Configuration.getInstance();
            Locale.getInstance();

            log = LoggerFactory.getLogger(Icarus.class);

            // The "Doom" ASCII from
            // http://patorjk.com/software/taag/#p=display&f=Doom&t=Icarus
            log.info(" _____                         ");
            log.info("|_   _|                        ");
            log.info("  | |  ___ __ _ _ __ _   _ ___ ");
            log.info("  | | / __/ _` | '__| | | / __|");
            log.info(" _| || (_| (_| | |  | |_| \\__ \\");
            log.info(" \\___/\\___\\__,_|_|   \\__,_|___/");
            log.info("");
            log.info("Icarus - Habbo Hotel PRODUCTION63 Server");
            log.info("Loading server...");
            log.info("");

            if (!Dao.connect()) {
                return;
            }

            log.info("Setting up game");

            GameSettings.getInstance();
            RoomManager.getInstance();
            NavigatorManager.getInstance();
            ItemManager.getInstance();
            CatalogueManager.getInstance();
            GameScheduler.getInstance();
            PetManager.getInstance();
            GroupManager.getInstance();
            CommandManager.getInstance();
            PluginManager.getInstance();

            log.info("Resetting users online...");
            SiteDao.updateKey(SiteKey.USERS_ONLINE, 0);

            log.info("Resetting room user counts...");
            RoomDao.updateUsers(-1, 0);

            log.info("Setting up server");

            // Get the server variables for the socket to listen on
            serverIP = Configuration.getInstance().getServerConfig().get("Server", "server.ip", String.class);
            serverPort = Configuration.getInstance().getServerConfig().get("Server", "server.port", int.class);

            // Override with valid IP that we have resolved
            if (!isValidIpAddress(serverIP)) {
                serverIP = InetAddress.getByName(serverIP).getHostAddress();
            }

            // Since we have overridden the IP, grab it again from the config to have a nice output instead of
            // something like 0.0.0.0 for instance ;)
            String configurationAddress = Configuration.getInstance().getServerConfig().get("Server", "server.ip", String.class);

            // Get the server instance through reflection
            Class<? extends ServerHandler> serverClass = Class.forName(Icarus.getServerClassPath()).asSubclass(ServerHandler.class);
            Constructor<? extends ServerHandler> serverConstructor = serverClass.getDeclaredConstructor(String.class, Integer.class);

            // Create the server instance
            server = serverConstructor.newInstance(serverIP, serverPort);
            server.createSocket();
            server.bind();

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

        String[] numbers = ip.split("\\.");

        if (numbers.length != 4) {
            return false;
        }

        for (String part : numbers) {

            if (!Util.isNumber(part)) {
                return false;
            }

            int number = Integer.valueOf(part);

            if (number > 255 || number < 0) {
                return false;
            }
        }

        return true;
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
}