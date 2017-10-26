package org.alexdev.icarus.web.routes.manager;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;

public interface Route {
    FullHttpResponse handleRoute(FullHttpRequest request, Channel channel);
}
