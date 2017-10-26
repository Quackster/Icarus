package org.alexdev.icarus.web.controllers.cookie;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import org.alexdev.icarus.web.routes.manager.Route;
import org.alexdev.icarus.web.server.response.WebResponse;
import org.alexdev.icarus.web.util.CookieUtil;

import java.util.concurrent.TimeUnit;

public class SetCookieController implements Route {
    @Override
    public FullHttpResponse handleRoute(FullHttpRequest request, Channel channel) {
        FullHttpResponse r = WebResponse.getHtmlResponse("Cookie set!");
        CookieUtil.set(r, "hello", "there!", 20, TimeUnit.SECONDS);
        return r;
    }
}
