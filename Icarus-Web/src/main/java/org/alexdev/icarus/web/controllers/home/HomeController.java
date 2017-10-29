package org.alexdev.icarus.web.controllers.home;

import io.netty.handler.codec.http.FullHttpResponse;
import org.alexdev.icarus.duckhttpd.server.session.WebSession;
import org.alexdev.icarus.duckhttpd.util.config.Settings;
import org.alexdev.icarus.duckhttpd.util.response.ResponseBuilder;

public class HomeController {

    public static FullHttpResponse index(WebSession webSession) {
        return ResponseBuilder.getHtmlResponse("Hello?");
    }
}
