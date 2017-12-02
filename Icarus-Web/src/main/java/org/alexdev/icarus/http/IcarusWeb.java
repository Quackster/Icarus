package org.alexdev.icarus.http;

import org.alexdev.duckhttpd.routes.RouteManager;
import org.alexdev.duckhttpd.server.WebServer;
import org.alexdev.duckhttpd.util.CompressionUtil;
import org.alexdev.duckhttpd.util.config.Settings;
import org.alexdev.icarus.http.game.GameSettings;
import org.alexdev.icarus.http.mysql.Storage;
import org.alexdev.icarus.http.mysql.dao.ItemDao;
import org.alexdev.icarus.http.util.config.Configuration;
import org.alexdev.icarus.http.util.web.ServerResponses;
import org.alexdev.icarus.http.util.Util;
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

        logger.info("Connecting to MySQL");
        Storage.get();

        if (Storage.get().isConnected()) {
            logger.info("Connection to MySQL server is successful");
        } else {
            logger.info("Connection to MySQL server was not successful");
        }

        GameSettings.getInstance();

        ItemDao.getRecentPhotos();

        Routes.register();
        logger.info("Registered " + RouteManager.getRoutes().size() + " route(s)!");

        /*Connection sqlConnection = null;
        PreparedStatement preparedStatement = null;
        ResultSet resultSet = null;

        try {

            sqlConnection = Storage.getInteractor().getConnection();
            preparedStatement = Storage.getInteractor().prepare("SELECT * FROM `site_config`", sqlConnection);
            resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                System.out.println(resultSet.getString("key").toUpperCase().replace(".", "_") + "(\"" + resultSet.getString("key") + "\"),");
            }

        } catch (Exception e) {
            Storage.logError(e);
        } finally {
            Storage.closeSilently(resultSet);
            Storage.closeSilently(preparedStatement);
            Storage.closeSilently(sqlConnection);
        }*/

        int port = 80;
        logger.info("Starting http service on port " + port);

        instance = new WebServer(port);
        instance.start();
    }

    public static Logger getErrorLogger() {
        return errorLogger;
    }
}
