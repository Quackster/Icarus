package org.alexdev.icarus.web.controllers.index;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import org.alexdev.icarus.web.routes.manager.Route;
import org.alexdev.icarus.web.server.response.WebResponse;

public class IndexController implements Route {

    @Override
    public FullHttpResponse handleRoute(FullHttpRequest request, Channel channel) {

        return WebResponse.getHtmlResponse("Hello!");
    }
}
