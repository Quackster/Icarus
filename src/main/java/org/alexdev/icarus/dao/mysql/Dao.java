package org.alexdev.icarus.dao.mysql;

import org.alexdev.icarus.util.config.Configuration;

public class Dao  {
    
    private static Storage storage;
    private static boolean isConnected;

    /**
     * Tries to connect to its data access object service
     * @return boolean - if connection was successful or not
     */
    public static boolean connect() {

        Storage.getLogger().info("Connecting to MySQL server");
        
        storage = new Storage(Configuration.getInstance().getServerConfig().get("Database", "mysql.hostname", String.class),
                Configuration.getInstance().getServerConfig().get("Database", "mysql.username", String.class),
                Configuration.getInstance().getServerConfig().get("Database", "mysql.password", String.class),
                Configuration.getInstance().getServerConfig().get("Database", "mysql.database", String.class));

        isConnected = storage.isConnected();

        if (!isConnected) {
        	Storage.getLogger().error("Could not connect");
        } else {
        	Storage.getLogger().info("Connection to MySQL was a success");
        }
        
        return isConnected;
    }

    /**
     * Returns the raw access to the data access object funcitons
     * @return {@link Dao} class
     */
    public static Storage getStorage() {
        return storage;
    }
}
