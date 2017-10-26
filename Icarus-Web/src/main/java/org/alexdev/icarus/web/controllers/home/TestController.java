package org.alexdev.icarus.web.controllers.home;

import io.netty.handler.codec.http.FullHttpResponse;
import org.alexdev.icarus.web.server.session.WebSession;
import org.alexdev.icarus.web.template.Template;

public class TestController {

    public static FullHttpResponse test(WebSession client) throws Exception {
        Template tpl = client.template();
        tpl.start("index");
        return tpl.render();
    }
}
