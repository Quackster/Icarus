package org.alexdev.icarus.web.controllers.home;

import org.alexdev.duckhttpd.server.connection.WebConnection;
import org.alexdev.duckhttpd.template.Template;
import org.alexdev.icarus.web.game.player.Player;
import org.alexdev.icarus.web.mysql.dao.PlayerDao;

public class HomeController {

    /**
     * Handle the /, /index and /homepage URI request
     * @param client the connection
     */
    public static void homepage(WebConnection client) {

        // If they are logged in, send them to the /me page
       if (client.session().getBoolean("authenticated")) {
           client.redirect("/me");
           return;
        }

        Template tpl = client.template("index");
        tpl.render();

        client.session().set("showAlert", false);
    }

    /**
     * Handle the /register URI request
     * @param client the connection
     */
    public static void register(WebConnection client) {

        // If they are logged in, stop them from registering
        if (client.session().getBoolean("authenticated")) {
            client.redirect("/me");
            return;
        }

        Template tpl = client.template("register");
        tpl.render();

        client.session().set("showAlert", false);
    }

    /**
     * Handle the /me URI request
     * @param client the connection
     */
    public static void me(WebConnection client) throws Exception {

        // If the user isn't logged, send them back to the index
        if (!client.session().getBoolean("authenticated")) {
            client.session().set("authenticated", false);
            client.redirect("/home");
            return;
        }

        // Creates a player instance and set it
        if (!client.session().contains("player")) {
            Player player = PlayerDao.get(client.session().getInt("userId"));
            client.session().set("player", player);
        }

        // nux
        if (client.session().get("player", Player.class).getName().isEmpty()) {
            client.redirect("/hotel");
            return;
        }

        Template tpl = client.template("me");
        tpl.render();
    }
}
