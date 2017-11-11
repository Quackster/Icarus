package org.alexdev.icarus.http.controllers.housekeeping;

import org.alexdev.duckhttpd.server.connection.WebConnection;
import org.alexdev.duckhttpd.template.Template;
import org.alexdev.icarus.http.game.player.Player;
import org.alexdev.icarus.http.mysql.dao.PlayerDao;
import org.alexdev.icarus.http.mysql.dao.housekeeping.HousekeepingPlayerDao;
import org.alexdev.icarus.http.util.piechart.PieChart;
import org.alexdev.icarus.http.util.SessionUtil;
import org.alexdev.icarus.http.util.piechart.Slice;
import org.alexdev.icarus.http.util.Util;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class HousekeepingController {

    /**
     * Handle the /housekeeping URI request
     *
     * @param client the connection
     */
    public static void dashboard(WebConnection client) {

        // If they are logged in, send them to the /me page
        if (!client.session().getBoolean(SessionUtil.LOGGED_IN_HOUSKEEPING)) {
            Template tpl = client.template("housekeeping/login");
            tpl.render();
        } else {

            int currentPage = 0;

            if (client.get().contains("page")) {
                currentPage = Integer.parseInt(client.get().get("page"));
            }

            Template tpl = client.template("housekeeping/dashboard");
            tpl.set("players", HousekeepingPlayerDao.getPlayers(currentPage));
            tpl.set("nextPlayers", HousekeepingPlayerDao.getPlayers(currentPage + 1));
            tpl.set("previousPlayers", HousekeepingPlayerDao.getPlayers(currentPage - 1));
            tpl.set("page", currentPage);
            tpl.render();
        }

        client.session().set("showAlert", false);
    }

    /**
     * Handle the /housekeeping/login URI request
     *
     * @param client the connection
     */
    public static void login(WebConnection client) {

        String[] fieldCheck = new String[] { "hkemail", "hkpassword" };

        for (String field : fieldCheck) {

            if (client.post().contains(field) &&
                client.post().get(field).length() > 0) {
                continue;
            }

            client.session().set("showAlert", true);
            client.session().set("alertType", "danger");
            client.session().set("alertMessage", "You need to enter both your email and password");
            client.redirect("/housekeeping");
            return;
        }

        if (!PlayerDao.emailExists(client.post().get("hkemail"), 0)) {
            client.session().set("showAlert", true);
            client.session().set("alertType", "danger");
            client.session().set("alertMessage", "You have entered invalid details");
            client.redirect("/housekeeping");
            return;
        }

        int userId = PlayerDao.valid(client.post().get("hkemail"), client.post().get("hkpassword"));

        if (userId == 0) {
            client.session().set("showAlert", true);
            client.session().set("alertType", "danger");
            client.session().set("alertMessage", "You have entered invalid details");
            client.redirect("/housekeeping");
            return;
        }

        Player player = PlayerDao.get(userId);

        if (!player.hasHouskeeping()) {
            client.session().set("showAlert", true);
            client.session().set("alertType", "warning");
            client.session().set("alertMessage", "You don't have permission");
            client.redirect("/housekeeping");
            return;
        }

        client.session().set(SessionUtil.LOGGED_IN_HOUSKEEPING, true);
        client.session().set(SessionUtil.USER_ID, userId);
        client.redirect("/housekeeping");
    }

    /**
     * Handle the /housekeeping/login URI request
     *
     * @param client the connection
     */
    public static void logout(WebConnection client) {

        if (client.session().getBoolean(SessionUtil.LOGGED_IN_HOUSKEEPING)) {
            client.session().set("showAlert", true);
            client.session().set("alertType", "success");
            client.session().set("alertMessage", "Successfully logged out!");
            client.session().set(SessionUtil.LOGGED_IN_HOUSKEEPING, false);
        }

        client.redirect("/housekeeping");
    }
}