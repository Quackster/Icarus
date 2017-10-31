package org.alexdev.icarus.web;

import io.netty.util.ResourceLeakDetector;
import org.alexdev.duckhttpd.routes.RouteManager;
import org.alexdev.duckhttpd.server.WebServer;
import org.alexdev.duckhttpd.util.config.Settings;
import org.alexdev.icarus.web.util.IcarusWebResponses;
import org.alexdev.icarus.web.util.config.Configuration;

public class IcarusWeb {

    private static Configuration config;
    private static WebServer instance;

    public static void main(String[] args) throws Exception {

        if (args.length < 1) {
            System.out.println("No arguments found, defaulting to port 80 for web server.");
            args = new String[] { "80"};
        }

        config = new Configuration();
        config.load();

        int port = Integer.parseInt(args[0]);
        System.out.println("Starting web service on port " + port);

        Settings settings = Settings.getInstance();
        settings.setWebResponses(new IcarusWebResponses());
        config.setSettings(settings);

        Routes.register();
        System.out.println("Registered " + RouteManager.getRoutes().size() + " route(s)!");

        ResourceLeakDetector.setLevel(ResourceLeakDetector.Level.ADVANCED);

        instance = new WebServer(port);
        instance.start();
    }
}
