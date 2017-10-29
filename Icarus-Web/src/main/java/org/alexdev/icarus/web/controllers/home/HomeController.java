package org.alexdev.icarus.web.controllers.home;

import io.netty.handler.codec.http.FullHttpResponse;
import org.alexdev.icarus.duckhttpd.server.session.WebSession;
import org.alexdev.icarus.duckhttpd.util.config.Settings;

public class HomeController {

    public static FullHttpResponse index(WebSession webSession) {
        return Settings.getInstance().getWebResponses().getNotFoundResponse();
    }
}
