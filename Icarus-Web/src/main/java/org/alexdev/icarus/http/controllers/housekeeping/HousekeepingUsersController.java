package org.alexdev.icarus.http.controllers.housekeeping;

import org.alexdev.duckhttpd.server.connection.WebConnection;
import org.alexdev.duckhttpd.template.Template;
import org.alexdev.icarus.http.game.player.Player;
import org.alexdev.icarus.http.mysql.dao.PlayerDao;
import org.alexdev.icarus.http.mysql.dao.housekeeping.HousekeepingPlayerDao;
import org.alexdev.icarus.http.util.SessionUtil;
import org.alexdev.icarus.http.util.config.Configuration;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.validator.routines.EmailValidator;

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

        if (client.post().queries().size() > 0) {

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
        tpl.set("defaultFigure", Configuration.REGISTER_FIGURE);
        tpl.set("defaultMission", Configuration.REGISTER_MOTTO);
        tpl.set("defaultCredits", Configuration.REGISTER_CREDITS);
        tpl.set("defaultDuckets", Configuration.REGISTER_DUCKETS);

        if (client.post().queries().size() > 0) {

            String[] fieldCheck = new String[]{"username", "password", "confirmpassword", "figure", "email", "mission"};

            for (String field : fieldCheck) {

                if (client.post().contains(field) && client.post().get(field).length() > 0) {
                    continue;
                }

                client.session().set("showAlert", true);
                client.session().set("alertType", "danger");
                client.session().set("alertMessage", "You need to enter all fields");
            }

            if (!client.session().getBoolean("showAlert")) {

                client.session().set("showAlert", true);
                client.session().set("alertType", "warning");

                if (PlayerDao.emailExists(client.post().get("email"), 0)) {
                    client.session().set("alertMessage", "The email chosen is already in use");

                } else if (!client.post().get("password").equals(client.post().get("confirmpassword"))) {
                    client.session().set("alertMessage", "The two passwords do not match");

                } else if (client.post().get("password").length() < 6) {
                    client.session().set("alertMessage", "The password needs to be at least 6 or more characters");

                } else if (!EmailValidator.getInstance().isValid(client.post().get("email"))) {
                    client.session().set("alertMessage", "The email entered is not valid");
                } else {
                    client.session().set("showAlert", false);
                }
            }
        }

        // Successful maybe?
        if (client.post().queries().size() > 0 && !client.session().getBoolean("showAlert")) {

            int userId = PlayerDao.create(client.post().get("username"), client.post().get("email"), client.post().get("password"), client.post().get("mission"), client.post().get("figure"));

            client.session().set("showAlert", true);
            client.session().set("alertType", "success");
            client.session().set("alertMessage", "The new user has been successfully created. <a href=\"/houskeeping/users/edit?id=" + userId + "\">Click here</a> to edit them.");
        }

        tpl.render();
        client.session().set("showAlert", false);
    }

    /**
     * Handle the /housekeeping/users/edit URI request
     *
     * @param client the connection
     */
    public static void edit(WebConnection client) {

        if (!client.session().getBoolean(SessionUtil.LOGGED_IN_HOUSKEEPING)) {
            client.redirect("/housekeeping");
            return;
        }

        Template tpl = client.template("housekeeping/users_edit");

        if (!client.get().contains("id")) {
            client.session().set("showAlert", true);
            client.session().set("alertType", "danger");
            client.session().set("alertMessage", "You did not select a user to edit");
        }

        if (client.post().queries().size() > 0) {

            String[] fieldCheck = new String[]{"username", "figure", "email", "mission", "credits", "duckets"};

            for (String field : fieldCheck) {

                if (client.post().contains(field) && client.post().get(field).length() > 0) {
                    continue;
                }

                client.session().set("showAlert", true);
                client.session().set("alertType", "danger");
                client.session().set("alertMessage", "You need to enter all fields " + field);
            }

            if (!client.session().getBoolean("showAlert")) {

                client.session().set("showAlert", true);
                client.session().set("alertType", "warning");

                if (PlayerDao.emailExists(client.post().get("email"), client.get().getInt("id"))) {
                    client.session().set("alertMessage", "The email chosen is already in use");
                } else if (!EmailValidator.getInstance().isValid(client.post().get("email"))) {
                    client.session().set("alertMessage", "The email entered is not valid");
                } else if (!StringUtils.isNumeric(client.post().get("credits"))) {
                    client.session().set("alertMessage", "The value supplied for credits is not a number");
                } else if (!StringUtils.isNumeric(client.post().get("duckets"))) {
                    client.session().set("alertMessage", "The value supplied for duckets is not a number");
                } else {
                    client.session().set("showAlert", false);
                }
            }
        }

        Player player = PlayerDao.get(client.get().getInt("id"));

        if (player == null) {
            client.session().set("showAlert", true);
            client.session().set("alertType", "danger");
            client.session().set("alertMessage", "The user does not exist");
        } else {

            Player session = client.session().get(SessionUtil.PLAYER, Player.class);

            tpl.set("playerId", player.getId());
            tpl.set("playerUsername", player.getName());
            tpl.set("playerEmail", player.getEmail());
            tpl.set("playerMission", player.getMission());
            tpl.set("playerDuckets", player.getDuckets());
            tpl.set("playerCredits", player.getCredits());
            tpl.set("playerFigure", player.getFigure());

            if (session.getRank() <= player.getRank()) {
                client.session().set("showAlert", true);
                client.session().set("alertType", "danger");
                client.session().set("alertMessage", "You cannot edit someone that is a lower rank than you");
            } else {


            }
        }

        tpl.render();
        client.session().set("showAlert", false);
    }
}
