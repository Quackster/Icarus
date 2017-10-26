package org.alexdev.icarus.web.controllers.home;

import io.netty.handler.codec.http.FullHttpResponse;
import org.alexdev.icarus.web.server.session.WebSession;
import org.alexdev.icarus.web.template.Template;

public class HomeController {

    /**
     * Handle client load controller
     *
     * @param client the client connection
     * @return the response, null if no response was needed
     * @throws Exception
     */
    public static FullHttpResponse client(WebSession client) throws Exception {
        Template tpl = client.template();
        tpl.start("client");
        tpl.set("sso-ticket", "123");
        return tpl.render();
    }
}
