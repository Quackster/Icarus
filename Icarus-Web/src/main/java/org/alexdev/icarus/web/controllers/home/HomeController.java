package org.alexdev.icarus.web.controllers.home;

import org.alexdev.icarus.duckhttpd.server.session.WebSession;
import org.alexdev.icarus.duckhttpd.template.Template;
import org.alexdev.icarus.web.util.config.Site;

public class HomeController {

    public static void homepage(WebSession webSession) throws Exception {
        Template tpl = webSession.template("index");
        tpl.set("site", new Site("http://localhost"));
        tpl.render();
    }

    public static void register(WebSession webSession) throws Exception {
        Template tpl = webSession.template("register");
        tpl.set("site", new Site("http://localhost"));
        tpl.render();
    }
}
