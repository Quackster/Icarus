package org.alexdev.icarus.web.controllers.home;

import org.alexdev.duckhttpd.server.connection.WebConnection;
import org.alexdev.duckhttpd.template.Template;

public class HomeController {

    /**
     * Handle the /, /index and /homepage URI request
     * @param client the connection
     */
    public static void homepage(WebConnection client) {

        // If they are logged in, send them to the /me page
       if (client.session().getBoolean("authenticated")) {
           client.redirect("/me");
           return;
        }

        Template tpl = client.template("index");
        tpl.render();

        client.session().set("showAlert", false);
    }

    /**
     * Handle the /register URI request
     * @param client the connection
     */
    public static void register(WebConnection client) {

        // If they are logged in, stop them from registering
        if (client.session().getBoolean("authenticated")) {
            client.redirect("/me");
            return;
        }

        Template tpl = client.template("register");
        tpl.render();

        client.session().set("showAlert", false);
    }

    /**
     * Handle the /me URI request
     * @param client the connection
     */
    public static void me(WebConnection client) throws Exception {

        // If the user isn't logged, send them back to the index
        if (!client.session().getBoolean("authenticated")) {
            client.session().set("authenticated", false);
            client.redirect("/home");
            return;
        }

        Template tpl = client.template("me");
        tpl.render();
    }
}
