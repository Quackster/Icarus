package org.alexdev.icarus.http.controllers.housekeeping;

import org.alexdev.duckhttpd.server.connection.WebConnection;
import org.alexdev.duckhttpd.template.Template;
import org.alexdev.icarus.http.mysql.dao.NewsDao;
import org.alexdev.icarus.http.util.SessionUtil;

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
}
