package org.alexdev.icarus;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;

import org.alexdev.icarus.dao.mysql.Dao;
import org.alexdev.icarus.game.GameScheduler;
import org.alexdev.icarus.game.catalogue.CatalogueManager;
import org.alexdev.icarus.game.furniture.ItemManager;
import org.alexdev.icarus.game.navigator.NavigatorManager;
import org.alexdev.icarus.game.room.RoomManager;
import org.alexdev.icarus.log.Log;
import org.alexdev.icarus.server.api.IServerHandler;
import org.alexdev.icarus.util.Util;
import org.luaj.vm2.Globals;
import org.luaj.vm2.LuaTable;
import org.luaj.vm2.LuaValue;
import org.luaj.vm2.lib.jse.CoerceJavaToLua;
import org.luaj.vm2.lib.jse.JsePlatform;

public class Icarus {

    private static IServerHandler server;

    private static String serverIP;
    private static String rawConfigIP;
    private static int serverPort;

    /**
     * Main call of Java application
     * @param args System arguments
     */
    public static void main(String[] args) {

        try {

            createConfig();
            Log.startup();

            rawConfigIP = Util.getConfiguration().get("Server", "server.ip", String.class);
            serverIP = rawConfigIP;
            
            if (!isValidIpAddress(rawConfigIP)) {
                serverIP = InetAddress.getByName(rawConfigIP).getHostAddress();
            }

            serverPort = Util.getConfiguration().get("Server", "server.port", int.class);
            
            if (!Dao.connect()) {
                return;
            }
            
            server = Class.forName(Icarus.getServerClassPath()).asSubclass(IServerHandler.class).newInstance();
            server.setIp(serverIP);
            server.setPort(serverPort);

            Log.println("Setting up game");
            
            RoomManager.load();
            NavigatorManager.load();
            ItemManager.load();
            CatalogueManager.load();
            GameScheduler.load();
            
            /*Globals globals = JsePlatform.standardGlobals();

            LuaValue test = CoerceJavaToLua.coerce(new MyClass());
            globals.set("obj", test);
            
            LuaValue chunk = globals.load("obj:test()");
            chunk.call();*/
            
            /*Globals globals = JsePlatform.standardGlobals();

            LuaValue test = CoerceJavaToLua.coerce(new MyClass());
            globals.set("obj", test);
            
            LuaValue chunk = globals.load("test = {\"test\", \"test2\"}");
            chunk.call();
            
            LuaValue tableValue = globals.get("test");
            
            if (tableValue.istable()) {
            	
            	LuaTable table = (LuaTable) tableValue;
            	Log.println("Amount of items: " + table.len());
            	
            	for (int i = 0; i < table.len().toint(); i++) {
            		
            		LuaValue value = table.get(i + 1);
            		
            		Log.println("Value " + i + ": " + value.toString()); 
            		
            	}
            	
            } else {
            	
            	Log.println("Amount of items in non-table: " + tableValue.checkint());
            	
            }*/
            
            Log.println("Settting up server");
            Log.println();
            
            if (server.listenSocket()) {
                Log.println("Server is listening on " + serverIP + ":" + serverPort);
            } else {
                Log.println("Server could not listen on " + serverPort + ":" + serverPort + ", please double check everything is correct in icarus.properties");
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Validate IP address that the server attempts to listen to
     * 
     * @param ip
     * @return true if valid IPv4
     */
    public static boolean isValidIpAddress (String ip) {
        try {
            String PATTERN = "^((0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)\\.){3}(0|1\\d?\\d?|2[0-4]?\\d?|25[0-5]?|[3-9]\\d?)$";
            return ip.matches(PATTERN);
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * Create the configuration files for this application, with the default values. Will throw an
     * exception if it could not create such files.
     * 
     * @throws IOException
     */
    private static void createConfig() throws IOException {
        File file = new File("icarus.properties");

        if (!file.isFile()) { 
            file.createNewFile();
            PrintWriter writer = new PrintWriter(file.getAbsoluteFile());
            writeMainConfiguration(writer);
            writer.flush();
            writer.close();
        }

        file = new File("habbohotel.properties");

        if (!file.isFile()) { 
            file.createNewFile();
            PrintWriter writer = new PrintWriter(file.getAbsoluteFile());
            writeHabboHotelConfiguration(writer);
            writer.flush();
            writer.close();
        }
        
        file = new File("locale.ini");

        if (!file.isFile()) { 
            file.createNewFile();
            PrintWriter writer = new PrintWriter(file.getAbsoluteFile());
            writeLocaleConfiguration(writer);
            writer.flush();
            writer.close();
        }


        Util.load();
    }

    /**
     * Writes default server configuration
     * 
     * @param writer - {@link PrintWriter} the file writer
     */
    private static void writeMainConfiguration(PrintWriter writer) {
        writer.println("[Server]");
        writer.println("server.ip=127.0.0.1");
        writer.println("server.port=30000");
        writer.println();
        writer.println("[Database]");
        writer.println("mysql.hostname=127.0.0.1");
        writer.println("mysql.username=user");
        writer.println("mysql.password=");
        writer.println("mysql.database=icarus");
        writer.println();
        writer.println("[Logging]");
        writer.println("log.errors=true");
        writer.println("log.output=true");
        writer.println("log.connections=true");
        writer.println("log.packets=true");
        writer.println();
    }

    /**
     * Writes default values for the game configuration
     * 
     * @param writer - {@link PrintWriter} the file writer
     */
    private static void writeHabboHotelConfiguration(PrintWriter writer) {
        writer.println("[Register]");
        writer.println("user.name.chars=1234567890qwertyuiopasdfghjklzxcvbnm-=?!@:.,");
        writer.println("user.default.credits=100");
        writer.println();
        writer.println("[Scheduler]");
        writer.println("credits.every.x.secs=600");
        writer.println("credits.every.x.amount=10");
        writer.println();
        writer.println("[Bot]");
        writer.println("bot.response.delay=1500");
        writer.println();
        writer.println("[Player]");
        writer.println("carry.drink.time=180");
        writer.println("carry.drink.interval=12");
        writer.println();
        writer.println("talking.lookat.distance=30");
        writer.println("talking.lookat.reset=6");
        writer.println();
        writer.println("afk.room.kick=1800");
        writer.println();
        writer.println("[Debug]");
        writer.println("debug.enable=true");
        writer.println();
    }
    
    /**
     * Writes default values for the locale
     * 
     * @param writer - {@link PrintWriter} the file writer
     */
    private static void writeLocaleConfiguration(PrintWriter writer) {
        writer.println("[Locale]");
        writer.println("language=English");
        writer.println();
        writer.println("[English]");
        writer.println("one.dimmer.per.room=You can only have one dimmer per room!");
        writer.println();
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
     * @return {@link IServerHandler} interface
     */
    public static IServerHandler getServer() {
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
}
