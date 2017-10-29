package org.alexdev.icarus.web;

import org.alexdev.icarus.duckhttpd.routes.RouteManager;
import org.alexdev.icarus.duckhttpd.server.WebServer;
import org.alexdev.icarus.duckhttpd.util.config.Settings;
import org.alexdev.icarus.web.template.TwigTemplate;
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
        settings.setSiteDirectory(config.values().get("Directories", "site.directory"));
        settings.setTemplateDirectory(config.values().get("Directories", "template.directory"));
        settings.setTemplateName(config.values().get("Template", "template.name"));
        settings.setTemplateHook(TwigTemplate.class);

        Routes.register();
        System.out.println("Registered " + RouteManager.getRoutes().size() + " route(s)!");

        instance = new WebServer(port);
        instance.start();
    }
}
