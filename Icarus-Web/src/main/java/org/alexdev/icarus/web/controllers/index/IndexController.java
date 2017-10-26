package org.alexdev.icarus.web.controllers.index;

import io.netty.buffer.ByteBuf;
import io.netty.channel.Channel;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.QueryStringDecoder;
import io.netty.util.CharsetUtil;
import org.alexdev.icarus.web.IcarusWeb;
import org.alexdev.icarus.web.routes.manager.Route;
import org.alexdev.icarus.web.server.response.WebResponse;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.nio.file.Paths;
import java.util.List;
import java.util.Map;

public class IndexController implements Route {

    @Override
    public FullHttpResponse handleRoute(FullHttpRequest request, Channel channel) {
        JtwigTemplate template = JtwigTemplate.fileTemplate(Paths.get(IcarusWeb.getContentDirectory(), "template/index.tpl").toString());
        JtwigModel model = JtwigModel.newModel().with("var", "World");

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

        return WebResponse.getHtmlResponse(template.render(model));
    }
}
