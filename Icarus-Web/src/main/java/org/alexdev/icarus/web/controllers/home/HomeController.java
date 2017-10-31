package org.alexdev.icarus.web.controllers.home;

import org.alexdev.duckhttpd.server.session.WebConnection;
import org.alexdev.duckhttpd.template.Template;

public class HomeController {

    public static void homepage(WebConnection client) throws Exception {

        // Load template
        Template tpl = client.template("index");
        tpl.render();

        // Unset alert after seeing it
        client.session().set("showAlert", false);
    }
}
