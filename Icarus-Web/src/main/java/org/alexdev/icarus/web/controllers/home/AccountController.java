package org.alexdev.icarus.web.controllers.home;

import org.alexdev.duckhttpd.server.session.WebConnection;

public class AccountController {

    public static void login(WebConnection client) {

        String[] fieldCheck = new String[] { "email", "password" };

        for (String field : fieldCheck) {

            if (client.post().excluded("email") ||
                client.post().get("email").isEmpty()) {
                continue;
            }

            client.session().set("showAlert", true);
            client.session().set("alertType", "error");
            client.session().set("alertMessage", "You need to enter both your email and password");
            client.redirect("/home");
        }
    }
}
