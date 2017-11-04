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
        if (!client.session().contains(SessionUtil.LOGGED_IN_HOUSKEEPING)) {
            Template tpl = client.template("housekeeping/login");
            tpl.render();
        } else {

            int currentPage = 1;

            if (client.get().contains("page")) {
                currentPage = Integer.parseInt(client.get().get("page"));
            }

            BufferedImage im = new BufferedImage(300, 150, BufferedImage.TYPE_INT_ARGB);
            List<Slice> slices = new ArrayList<>();
            slices.add(new Slice("Past day", 10, Color.cyan));
            slices.add(new Slice("Past month", 15, Color.green));
            slices.add(new Slice("Past year", 30, Color.orange));
            slices.add(new Slice("All time", 45, Color.blue));
            PieChart pc = new PieChart(im, slices);
            Template tpl = client.template("housekeeping/dashboard");
            tpl.set("pieChartImage1", Util.convert(im));
            tpl.set("pieChartLabel1", "New Users");
            tpl.set("pieChartText1", "A chart showing all users who have ever registered");

            im = new BufferedImage(300, 150, BufferedImage.TYPE_INT_ARGB);
            slices = new ArrayList<>();
            slices.add(new Slice("Over 10,000", 6, Color.orange));
            slices.add(new Slice("Over 5,000", 10, Color.MAGENTA));
            slices.add(new Slice("Over 2,000", 29, Color.CYAN));
            slices.add(new Slice("Over 1,000", 66, Color.yellow));
            pc = new PieChart(im, slices);
            tpl.set("pieChartImage2", Util.convert(im));
            tpl.set("pieChartLabel2", "Economy");
            tpl.set("pieChartText2", "A chart showing all users who own coins under or over a certain amount");
            tpl.render();

            im = new BufferedImage(300, 150, BufferedImage.TYPE_INT_ARGB);
            slices = new ArrayList<>();
            slices.add(new Slice("Over 10,000", 6, Color.orange));
            slices.add(new Slice("Over 5,000", 10, Color.blue));
            slices.add(new Slice("Over 2,000", 29, Color.green));
            slices.add(new Slice("Over 1,000", 66, Color.yellow));
            pc = new PieChart(im, slices);
            tpl.set("pieChartImage3", Util.convert(im));
            tpl.set("pieChartLabel3", "Economy");
            tpl.set("pieChartText3", "A chart showing all users who own coins under or over a certain amount");

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

        if (!PlayerDao.emailExists(client.post().get("hkemail"))) {
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
}