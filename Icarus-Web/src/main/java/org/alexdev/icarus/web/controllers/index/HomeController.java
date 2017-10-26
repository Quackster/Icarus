package org.alexdev.icarus.web.controllers.index;

import io.netty.channel.Channel;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import org.alexdev.icarus.web.server.response.WebResponse;

public class HomeController {

    public static FullHttpResponse index(FullHttpRequest request, Channel channel) {

        //POST DATA
        /*QueryStringDecoder decoder = new QueryStringDecoder("?" +
                request.content().toString(CharsetUtil.UTF_8));

        if (decoder.parameters().size() > 0) {
            System.out.println(decoder.parameters().get("xd").get(0));
        }*/

        //GET DATA
        /*QueryStringDecoder decoder = new QueryStringDecoder( request.uri());

        if (decoder.parameters().size() > 0) {
            for (Map.Entry<String, List<String>> set : decoder.parameters().entrySet()) {
                System.out.println(set.getKey() + " -- " + set.getValue().get(0));
            }
        }*/

        return WebResponse.getHtmlResponse("Welcome page");
    }

    public static FullHttpResponse index_test(FullHttpRequest fullHttpRequest, Channel channel) {
        return WebResponse.getHtmlResponse("Sup nibba");
    }
}
