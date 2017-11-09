package org.alexdev.icarus.http.controllers.housekeeping;

import org.alexdev.duckhttpd.server.connection.WebConnection;
import org.alexdev.duckhttpd.template.Template;
import org.alexdev.icarus.http.game.player.Player;
import org.alexdev.icarus.http.mysql.dao.housekeeping.HousekeepingPlayerDao;
import org.alexdev.icarus.http.util.SessionUtil;

import java.util.ArrayList;
import java.util.List;

public class HousekeepingUsersController {

    /**
     * Handle the /housekeeping/users/search URI request
     *
     * @param client the connection
     */
    public static void search(WebConnection client) {

        if (!client.session().getBoolean(SessionUtil.LOGGED_IN_HOUSKEEPING)) {
            client.redirect("/housekeeping");
            return;
        }

        Template tpl = client.template("housekeeping/users_search");

        if (client.post().getQueries().size() > 0) {

            String[] fieldCheck = new String[]{"searchField", "searchQuery", "searchType" };

            for (String field : fieldCheck) {

                if (client.post().contains(field) && client.post().get(field).length() > 0) {
                    continue;
                }

                client.session().set("showAlert", true);
                client.session().set("alertType", "danger");
                client.session().set("alertMessage", "You need to enter all fields");
                tpl.render();
                client.session().set("showAlert", false);
                return;
            }

            String field = client.post().get("searchField");
            String input = client.post().get("searchQuery");
            String type = client.post().get("searchType");

            List<String> whitelistColumns = new ArrayList<>();
            whitelistColumns.add("username");
            whitelistColumns.add("id");
            whitelistColumns.add("credits");
            whitelistColumns.add("duckets");
            whitelistColumns.add("mission");

            List<Player> players = null;

            if (whitelistColumns.contains(field)) {
                players = HousekeepingPlayerDao.search(type, field, input);
            } else {
                players = new ArrayList<>();
            }

            tpl.set("players", players);
        }

        tpl.render();
    }

    /**
     * Handle the /housekeeping/users/create URI request
     *
     * @param client the connection
     */
    public static void create(WebConnection client) {

        if (!client.session().getBoolean(SessionUtil.LOGGED_IN_HOUSKEEPING)) {
            client.redirect("/housekeeping");
            return;
        }

        Template tpl = client.template("housekeeping/users_create");
        tpl.render();
    }
}
