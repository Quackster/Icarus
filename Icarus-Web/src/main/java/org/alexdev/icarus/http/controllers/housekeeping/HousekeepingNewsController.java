package org.alexdev.icarus.http.controllers.housekeeping;

import org.alexdev.duckhttpd.server.connection.WebConnection;
import org.alexdev.duckhttpd.template.Template;
import org.alexdev.duckhttpd.util.config.Settings;
import org.alexdev.icarus.http.game.player.Player;
import org.alexdev.icarus.http.mysql.dao.NewsDao;
import org.alexdev.icarus.http.util.SessionUtil;

import java.io.File;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class HousekeepingNewsController {

    public static void articles(WebConnection client) {

        if (!client.session().getBoolean(SessionUtil.LOGGED_IN_HOUSKEEPING)) {
            client.redirect("/housekeeping");
            return;
        }

        Template tpl = client.template("housekeeping/articles");
        tpl.set("articles", NewsDao.getArticles());
        tpl.render();
    }

    public static void create(WebConnection client) {

        if (!client.session().getBoolean(SessionUtil.LOGGED_IN_HOUSKEEPING)) {
            client.redirect("/housekeeping");
            return;
        }

        if (client.post().queries().size() > 0) {

            Player session = client.session().get(SessionUtil.PLAYER, Player.class);


            NewsDao.create(
                client.post().get("title"),
                client.post().get("shortstory"),
                client.post().get("fullstory"),
                client.post().get("topstory"),
                session.getName()
            );
        }

        Template tpl = client.template("housekeeping/articles_create");
        tpl.set("images", getTopStoryImages());
        tpl.render();
    }

    public static List<String> getTopStoryImages() {

        List<String> images = new ArrayList<String>();

        for (File file : Paths.get(Settings.getInstance().getSiteDirectory(), "c_images", "Top_Story_Images").toFile().listFiles()) {

            if (!file.getName().contains(".png")) {
                continue;
            }

            images.add(file.getName());
        }

        return images;
    }
}
