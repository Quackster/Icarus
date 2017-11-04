package org.alexdev.icarus.http;

import org.alexdev.duckhttpd.routes.RouteManager;
import org.alexdev.duckhttpd.server.WebServer;
import org.alexdev.duckhttpd.util.config.Settings;
import org.alexdev.icarus.http.util.PieChart;
import org.alexdev.icarus.http.util.web.ServerResponses;
import org.alexdev.icarus.http.util.web.Routes;
import org.alexdev.icarus.http.util.config.Configuration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.File;

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

        try {

            BufferedImage im = new BufferedImage(500, 500, BufferedImage.TYPE_INT_ARGB);
            PieChart pieChart = new PieChart(im);

            File outputfile = new File("image.png");
            ImageIO.write(im, "png", outputfile);


        } catch (Exception e) {
            e.printStackTrace();
        }

        int port = 80;
        logger.info("Starting http service on port " + port);

        Routes.register();
        logger.info("Registered " + RouteManager.getRoutes().size() + " route(s)!");

        instance = new WebServer(port);
        instance.start();
    }

    public static Logger getErrorLogger() {
        return errorLogger;
    }
}
