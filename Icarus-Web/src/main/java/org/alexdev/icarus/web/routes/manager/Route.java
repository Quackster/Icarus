package org.alexdev.icarus.web.routes.manager;

import io.netty.handler.codec.http.FullHttpResponse;
import org.alexdev.icarus.web.server.session.WebSession;

public interface Route {
    FullHttpResponse handleRoute(WebSession client) throws Exception;
}
