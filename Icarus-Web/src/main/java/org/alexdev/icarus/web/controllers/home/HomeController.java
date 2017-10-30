package org.alexdev.icarus.web.controllers.home;

import org.alexdev.icarus.duckhttpd.server.session.WebSession;
import org.alexdev.icarus.duckhttpd.template.Template;
import org.alexdev.icarus.duckhttpd.util.response.ResponseBuilder;
import org.alexdev.icarus.web.template.site.Site;

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

    public static void me(WebSession webSession) throws Exception {
        Template tpl = webSession.template("me");
        tpl.set("site", new Site("http://localhost"));
        tpl.render();
    }

    public static void client(WebSession webSession) throws Exception {
        Template tpl = webSession.template("client");
        tpl.set("site", new Site("http://localhost"));
        tpl.render();
    }

    public static void hotel(WebSession webSession) throws Exception {
        Template tpl = webSession.template("hotel");
        tpl.set("site", new Site("http://localhost"));
        tpl.render();
    }

    public static void clienturl(WebSession webSession) {
        String clientUrl = "http://localhost/client";
        webSession.setResponse(ResponseBuilder.getHtmlResponse("{\"clienturl\":\"" + clientUrl + "\"}"));
    }
}
