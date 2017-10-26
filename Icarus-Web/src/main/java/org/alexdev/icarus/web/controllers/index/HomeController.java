package org.alexdev.icarus.web.controllers.index;

import io.netty.handler.codec.http.FullHttpResponse;
import org.alexdev.icarus.web.server.response.WebResponse;
import org.alexdev.icarus.web.server.session.WebSession;

public class HomeController {

    public static FullHttpResponse index(WebSession client) {

        if (!client.session().contains("lol")) {
            client.session().set("lol", "Hello there");
            return WebResponse.getHtmlResponse("Added to session");
        } else {
            return WebResponse.getHtmlResponse(client.session().get("lol"));
        }

    }

    public static FullHttpResponse index_test(WebSession client) {
        return WebResponse.getHtmlResponse("Sup nibba");
    }
}
