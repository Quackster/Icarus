package org.alexdev.icarus.util.config;

import org.alexdev.icarus.log.Log;
import org.apache.log4j.PropertyConfigurator;
import org.ini4j.Wini;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;

public class Configuration {

    private Wini configuration;
    private Wini gameConfig;

    private static Configuration instance;

    public Configuration() {
        try {
            checkLog4j();

            writeFileIfNotExist();
            configuration = new Wini(new File("icarus.properties"));
            gameConfig = new Wini(new File("game.properties"));
        } catch (Exception e) {
            Log.getErrorLogger().error("Unhandled exception while loading the configurion: ", e);
        }
    }

	/**
     * Create the configuration files for this application, with the default values. Will throw an
     * exception if it could not create such files.
     * 
     * @throws IOException
     */
    private void checkLog4j() throws FileNotFoundException {

    	String output = "log4j.rootLogger=INFO, stdout\n" +
                "log4j.appender.stdout.threshold=info\n" +
                "log4j.appender.stdout=org.apache.log4j.ConsoleAppender\n" +
                "log4j.appender.stdout.Target=System.out\n" +
                "log4j.appender.stdout.layout=org.apache.log4j.PatternLayout\n" +
                "log4j.appender.stdout.layout.ConversionPattern=%d{yyyy-MM-dd'T'HH:mm:ss.SSS} %-5p [%c] - %m%n\n" +
                "\n" +
                "# Create new logger information for error\n" +
                "log4j.logger.ErrorLogger=ERROR, error, FILE\n" +
                "log4j.additivity.ErrorLogger=false\n" +
                "\n" +
                "# Set settings for the error logger\n" +
                "log4j.appender.error=org.apache.log4j.ConsoleAppender\n" +
                "log4j.appender.error.Target=System.err\n" +
                "log4j.appender.error.layout=org.apache.log4j.PatternLayout\n" +
                "log4j.appender.error.layout.ConversionPattern=%d{yyyy-MM-dd'T'HH:mm:ss.SSS} %-5p [%c] - %m%n\n" +
                "\n" +
                "# Define the file appender\n" +
                "log4j.appender.FILE=org.apache.log4j.FileAppender\n" +
                "log4j.appender.FILE.File=error.log\n" +
                "log4j.appender.FILE.ImmediateFlush=true\n" +
                "log4j.appender.FILE.Threshold=debug\n" +
                "log4j.appender.FILE.Append=false\n" +
                "log4j.appender.FILE.layout=org.apache.log4j.PatternLayout\n" +
                "log4j.appender.FILE.layout.conversionPattern=%d{yyyy-MM-dd'T'HH:mm:ss.SSS} - [%c] - %m%n\n";
    	
    	File loggingConfig = new File("log4j.properties");
    	
    	if (!loggingConfig.exists()) {
            PrintWriter writer = new PrintWriter(loggingConfig.getAbsoluteFile());
            writer.write(output);
            writer.flush();
            writer.close();
    	}

    	// Change the path where the logger property should be read from
    	PropertyConfigurator.configure(loggingConfig.getAbsolutePath());

    	//...after this, we should be able to use Log4j (yay!)
        // (hopefully, anyways, if no one operating this server screwed something up).
	}

	private void writeFileIfNotExist() throws IOException {
        File file = new File("icarus.properties");
        
        if (!file.isFile()) { 
            file.createNewFile();
            PrintWriter writer = new PrintWriter(file.getAbsoluteFile());
            writeMainConfiguration(writer);
            writer.flush();
            writer.close();
        }

        file = new File("game.properties");

        if (!file.isFile()) { 
            file.createNewFile();
            PrintWriter writer = new PrintWriter(file.getAbsoluteFile());
            writeGameConfiguration(writer);
            writer.flush();
            writer.close();
        }

        file = new File("plugins");

        if (!file.exists()) { 
            file.mkdir();
        }
        
        file = new File("plugins" + File.separator + "plugin_registry.lua");

        if (!file.isFile()) {
            file.createNewFile();
            PrintWriter writer = new PrintWriter(file.getAbsoluteFile());
            writePluginConfiguration(writer);
            writer.flush();
            writer.close();
        }
	}

	/**
     * Writes default server configuration
     * 
     * @param writer - {@link PrintWriter} the file writer
     */
    private void writeMainConfiguration(PrintWriter writer) {
        writer.println("[Server]");
        writer.println("server.ip=127.0.0.1");
        writer.println("server.port=30001");
        writer.println();
        writer.println("[Database]");
        writer.println("mysql.hostname=127.0.0.1");
        writer.println("mysql.username=user");
        writer.println("mysql.password=");
        writer.println("mysql.database=icarusdb");
        writer.println();
        writer.println("[Logging]");
        writer.println("log.connections=true");
        writer.println("log.sent.packets=false");
        writer.println("log.received.packets=false");
        writer.println();
        writer.println("log.items.loaded=true");
        writer.println();
    }

    /**
     * Writes default values for the game configuration
     * 
     * @param writer - {@link PrintWriter} the file writer
     */
    private void writeGameConfiguration(PrintWriter writer) {
        writer.println("[Scheduler]");
        writer.println("credits.interval.minutes=10");
        writer.println("credits.interval.amount=100");
        writer.println();
        writer.println("duckets.interval.minutes=15");
        writer.println("duckets.interval.amount=30");
        writer.println();
        writer.println("[Navigator]");
        writer.println("max.rooms.popular.tab=30");
        writer.println("max.room.sub.category=9");
        writer.println("max.room.per.user=25");
        writer.println();
        writer.println("[Room]");
        writer.println("afk.room.kick=1800");
        writer.println();
        writer.println("[Camera]");
        writer.println("camera.enabled=true");
        writer.println("camera.path=D:/xampp/htdocs/c_images/camera_photos/");
        writer.println("camera.filename=room_{id}_{username}_{generatedId}.png");
        writer.println();
        writer.println("[Thumbnail]");
        writer.println("thumbnail.create.enabled=true");
        writer.println("thumbnail.path=D:/xampp/htdocs/c_images/thumbnails/");
        writer.println("thumbnail.url=thumbnails/{filename}");
        writer.println("thumbnail.filename=room_{id}_{generatedId}.png");
        writer.println();
        writer.println("[Debug]");
        writer.println("debug.enable=true");
        writer.println();
    }
    
    /**
     * Writes default values for the plugin registry file
     * 
     * @param writer - {@link PrintWriter} the file writer
     */
    private static void writePluginConfiguration(PrintWriter writer) {
        writer.println("--[[\r\n" + 
                "    Array for storing plugin .lua files to be registered \r\n" + 
                "    by the server\r\n" + 
                "    \r\n" + 
                "    Example:\r\n" + 
                "    \r\n" + 
                "        plugins = {\r\n" + 
                "            \"ExamplePlugin/example_plugin.lua\"\r\n" + 
                "        }\r\n" + 
                "    \r\n" + 
                "--]]\r\n" + 
                "plugins = {}");
        writer.println();
    }

    /**
     * Get the main server configuration that controls logging, database access,
     * and server details.
     *
     * @return the configuration
     */
    public Wini getServerConfig() {
        return configuration;
    }

    /**
     * Get the game configuration that controls the variables that influence gameplay.
     *
     * @return the configuration
     */
    public Wini getGameConfig() {
        return gameConfig;
    }

    /**
     * Gets the instance
     *
     * @return the instance
     */
    public static Configuration getInstance() {

        if (instance == null) {
            instance = new Configuration();
        }

        return instance;
    }
}
