package org.alexdev.icarus.web.controllers.cookie;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import org.alexdev.icarus.web.routes.manager.Route;
import org.alexdev.icarus.web.server.response.WebResponse;
import org.alexdev.icarus.web.util.CookieUtil;

public class GetCookieController implements Route {
    @Override
    public FullHttpResponse handleRoute(FullHttpRequest request, Channel channel) {

        String response = "doesnt exist!";

        if (CookieUtil.exists(request,"hello")) {
            response = "it does exist!";
        }

        FullHttpResponse r = WebResponse.getHtmlResponse(response);
        return r;
    }
}
