package org.alexdev.icarus.web.controllers.home;

import io.netty.handler.codec.http.FullHttpResponse;
import org.alexdev.icarus.web.server.session.WebSession;
import org.alexdev.icarus.web.template.Template;

public class HomeController {

    public static FullHttpResponse index(WebSession client) throws Exception {
        Template tpl = client.template();
        tpl.start("index");
        return tpl.render();
    }

    public static FullHttpResponse me(WebSession client) throws Exception {
        Template tpl = client.template();
        tpl.start("home");
        return tpl.render();
    }
}
