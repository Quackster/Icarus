package org.alexdev.icarus.web.controllers.home;

import org.alexdev.duckhttpd.server.connection.WebConnection;
import org.alexdev.duckhttpd.template.Template;

public class HomeController {

    public static void homepage(WebConnection client) throws Exception {

       if (client.session().getBoolean("authenticated")) {
           client.redirect("/me");
           return;
        }

        Template tpl = client.template("index");
        tpl.render();

        client.session().set("showAlert", false);

    }
}
