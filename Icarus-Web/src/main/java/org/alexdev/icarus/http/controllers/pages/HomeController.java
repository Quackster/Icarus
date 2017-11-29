package org.alexdev.icarus.http.controllers.pages;

import org.alexdev.duckhttpd.server.connection.WebConnection;
import org.alexdev.duckhttpd.template.Template;
import org.alexdev.icarus.http.game.news.NewsArticle;
import org.alexdev.icarus.http.game.player.Player;
import org.alexdev.icarus.http.mysql.dao.ItemDao;
import org.alexdev.icarus.http.mysql.dao.NewsDao;
import org.alexdev.icarus.http.util.SessionUtil;
import org.alexdev.icarus.util.Util;

import java.util.List;

public class HomeController {

    /**
     * Handle the /, /index and /homepage URI request
     * @param client the connection
     */
    public static void homepage(WebConnection client) {

        // If they are logged in, send them to the /me page
       if (client.session().getBoolean(SessionUtil.LOGGED_IN)) {
           client.redirect("/me");
           return;
        }

        Template tpl = client.template("index");
        tpl.render();

        // Show the alert only once...
        client.session().set("showAlert", false);
    }

    /**
     * Handle the /register URI request
     * @param client the connection
     */
    public static void register(WebConnection client) {

        // If they are logged in, stop them from registering
        if (client.session().getBoolean(SessionUtil.LOGGED_IN)) {
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
        if (!client.session().getBoolean(SessionUtil.LOGGED_IN)) {
            client.redirect("/home");
            return;
        }

        // nux
        if (client.session().get("player", Player.class).getName().isEmpty()) {
            client.redirect("/hotel");
            return;
        }

        Template tpl = client.template("me");
        tpl.render();
    }

    /**
     * Handle the /disconnect URI request
     * @param client the connection
     */
    public static void disconnected(WebConnection client) throws Exception {
        Template tpl = client.template("error");
        tpl.set("errorTitle", "Disconnected!");
        tpl.set("errorMessage", "Looks like you were disconnected!<br><br>" + client.post().queries().toString() + "");
        tpl.render();
    }

    /**
     * Handle the /article URI request
     * @param client the connection
     */
    public static void article(WebConnection client) {

        NewsArticle article = NewsDao.get(client.get().getInt("id"));

        Template tpl = client.template("article");
        tpl.set("article", article);
        tpl.render();
    }

    /**
     * Handle the /community URI request
     * @param client the connection
     */
    public static void community(WebConnection client) {

        List<String> photos = ItemDao.getRecentPhotos();
        List<List<String>> images = Util.paginate(photos, 4);

        Template tpl = client.template("community");
        tpl.set("images", images);
        tpl.render();
    }
}
