package org.alexdev.icarus.web.controllers.home;

import org.alexdev.duckhttpd.server.session.WebConnection;

public class AccountController {

    public static void login(WebConnection client) {

        if (!client.post().contains("username")) {
            client.session().set("showAlert", true);
            client.session().set("alertType", "warning");
            client.session().set("alertMessage", "hello!");
            client.redirect("home");
        }
    }
}
