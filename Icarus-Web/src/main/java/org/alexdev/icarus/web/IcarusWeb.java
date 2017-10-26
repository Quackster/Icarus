package org.alexdev.icarus.web;

import org.alexdev.icarus.web.routes.manager.RouteManager;
import org.alexdev.icarus.web.routes.Routes;
import org.alexdev.icarus.web.server.NettyWebServer;

public class IcarusWeb {

    private static String contentDirectory = "C:/Users/Alex/Documents/GitHub/Icarus/Icarus-Web/site";
    private static NettyWebServer instance;

    public static void main(String[] args) {

        if (args.length < 1) {
            System.out.println("No arguments found, defaulting to port 80 for web server.");
            args = new String[] { "80"};
        }

        int port = Integer.parseInt(args[0]);
        System.out.println("Starting web service on port " + port);

        Routes.register();
        System.out.println("Registered " + RouteManager.getRoutes().size() + " route(s)!");

        instance = new NettyWebServer(port);
        instance.start();
    }

    public static String getContentDirectory() {
        return contentDirectory;
    }
}
