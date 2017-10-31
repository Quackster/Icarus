package org.alexdev.icarus.web.controllers.home;

import org.alexdev.duckhttpd.server.session.WebConnection;
import org.alexdev.duckhttpd.template.Template;

public class AccountController {

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
            client.redirect("/home");
            return;
        }

        client.redirect("/me");
    }

    public static void me(WebConnection client) throws Exception {

        Template tpl = client.template("me");
        tpl.render();
    }
}
