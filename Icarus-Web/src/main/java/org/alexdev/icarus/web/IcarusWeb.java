package org.alexdev.icarus.web;

import org.alexdev.icarus.web.routes.Routes;
import org.alexdev.icarus.web.routes.manager.RouteManager;
import org.alexdev.icarus.web.server.NettyWebServer;
import org.alexdev.icarus.web.util.config.Configuration;

public class IcarusWeb {

    private static NettyWebServer instance;
    private static Configuration config;

    public static void main(String[] args) throws Exception {

        if (args.length < 1) {
            System.out.println("No arguments found, defaulting to port 80 for web server.");
            args = new String[] { "80"};
        }

        config = new Configuration();
        config.checkFileExistence();
        config.load();

        int port = Integer.parseInt(args[0]);
        System.out.println("Starting web service on port " + port);

        Routes.register();
        System.out.println("Registered " + RouteManager.getRoutes().size() + " route(s)!");

        instance = new NettyWebServer(port);
        instance.start();
    }

    public static String getSiteDirectory() {
        return config.values().get("Directories", "site.directory", String.class);
    }

    public static String getTemplateDirectory() {
        return config.values().get("Directories", "template.directory", String.class);
    }

    public static String getTemplateName() {
        return config.values().get("Template", "template.name", String.class);
    }
}
