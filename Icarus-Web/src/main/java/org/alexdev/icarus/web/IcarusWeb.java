package org.alexdev.icarus.web;

import org.alexdev.duckhttpd.routes.RouteManager;
import org.alexdev.duckhttpd.server.WebServer;
import org.alexdev.duckhttpd.util.config.Settings;
import org.alexdev.icarus.web.responses.IcarusWebResponses;
import org.alexdev.icarus.web.util.config.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IcarusWeb {

    private static Logger logger = LoggerFactory.getLogger(IcarusWeb.class);
    private static Logger errorLogger = LoggerFactory.getLogger("ErrorLogger");

    private static Configuration config;
    private static WebServer instance;

    public static void main(String[] args) throws Exception {

        config = new Configuration();
        config.load();

        if (args.length < 1) {
            logger.warn("No arguments found, defaulting to port 80 for web server.");
            args = new String[] { "80"};
        }

        int port = Integer.parseInt(args[0]);
        logger.info("Starting web service on port " + port);

        Settings settings = Settings.getInstance();
        settings.setResponses(new IcarusWebResponses());
        config.setSettings(settings);

        Routes.register();
        logger.info("Registered " + RouteManager.getRoutes().size() + " route(s)!");

        instance = new WebServer(port);
        instance.start();
    }

    public static Logger getErrorLogger() {
        return errorLogger;
    }
}
