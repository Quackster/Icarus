package org.alexdev.icarus.http.controllers.account;

import org.alexdev.duckhttpd.server.connection.WebConnection;
import org.alexdev.icarus.http.mysql.dao.PlayerDao;

public class AccountController {

    /**
     * Handle the /account/login URI request
     * @param client the connection
     */
    public static void login(WebConnection client) {

        String[] fieldCheck = new String[] { "email", "password" };

        for (String field : fieldCheck) {

            if (client.post().contains(field) &&
                client.post().get(field).length() > 0) {
                continue;
            }

            client.session().set("showAlert", true);
            client.session().set("alertType", "error");
            client.session().set("alertMessage", "You need to enter both your email and password");
            client.redirect("/");
            return;
        }

        if (!PlayerDao.emailExists(client.post().get("email"))) {
            client.session().set("showAlert", true);
            client.session().set("alertType", "error");
            client.session().set("alertMessage", "That account does not exist!");
            client.redirect("/");
            return;
        }

        int userId = PlayerDao.valid(client.post().get("email"), client.post().get("password"));

        if (userId == 0) {
            client.session().set("showAlert", true);
            client.session().set("alertType", "error");
            client.session().set("alertMessage", "You have entered an invalid password");
            client.redirect("/");
            return;
        }

        client.session().set("authenticated", true);
        client.session().set("userId", userId);
        client.redirect("/me");
    }

    /**
     * Handle the /logout URI request
     * @param client the connection
     */
    public static void logout(WebConnection client) {
        client.session().set("showAlert", true);
        client.session().set("alertType", "success");
        client.session().set("alertMessage", "Successfully logged out!");
        client.session().set("authenticated", false);
        client.redirect("/");
    }
}
