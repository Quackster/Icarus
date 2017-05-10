package org.alexdev.icarus.dao.mysql;

import org.alexdev.icarus.log.Log;
import org.alexdev.icarus.util.Util;

public class Dao  {
    
    private static Storage storage;
    private static boolean isConnected;

    /**
     * Tries to connect to its data access object service
     * @return boolean - if connection was successful or not
     */
    public static boolean connect() {

        Log.println("Connecting to MySQL server");
        
        storage = new Storage(Util.getConfiguration().get("Database", "mysql.hostname", String.class), 
                Util.getConfiguration().get("Database", "mysql.username", String.class), 
                Util.getConfiguration().get("Database", "mysql.password", String.class), 
                Util.getConfiguration().get("Database", "mysql.database", String.class)); 

        isConnected = storage.isConnected();

        if (!isConnected) {
            Log.println("Could not connect");
        } else {
            Log.println("Connection to MySQL was a success");
        }
        
        Log.println();
        
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
