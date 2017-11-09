package org.alexdev.icarus.http;

import org.alexdev.duckhttpd.routes.RouteManager;
import org.alexdev.duckhttpd.server.WebServer;
import org.alexdev.duckhttpd.util.config.Settings;
import org.alexdev.icarus.http.util.web.ServerResponses;
import org.alexdev.icarus.http.util.config.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class IcarusWeb {

    private static Logger logger = LoggerFactory.getLogger(IcarusWeb.class);
    private static Logger errorLogger = LoggerFactory.getLogger("ErrorLogger");

    private static Configuration config;
    private static WebServer instance;

    public static void main(String[] args) throws Exception {

        Settings settings = Settings.getInstance();
        settings.setResponses(new ServerResponses());

        config = new Configuration();
        config.load();
        config.setSettings(settings);

        Routes.register();
        logger.info("Registered " + RouteManager.getRoutes().size() + " route(s)!");

        int port = 80;
        logger.info("Starting http service on port " + port);

        instance = new WebServer(port);
        instance.start();
    }

    public static Logger getErrorLogger() {
        return errorLogger;
    }
}
