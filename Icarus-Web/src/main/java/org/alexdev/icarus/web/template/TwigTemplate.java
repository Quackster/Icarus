package org.alexdev.icarus.web.template;

import io.netty.handler.codec.http.FullHttpResponse;
import org.alexdev.duckhttpd.server.session.WebConnection;
import org.alexdev.duckhttpd.template.Template;
import org.alexdev.duckhttpd.util.config.Settings;
import org.alexdev.duckhttpd.response.ResponseBuilder;
import org.alexdev.icarus.web.template.site.IcarusSession;
import org.alexdev.icarus.web.template.site.Site;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.File;
import java.nio.file.Paths;

public class TwigTemplate extends Template {

    private File file;
    private JtwigModel model;
    private JtwigTemplate template;

    public TwigTemplate(WebConnection session) {
        super(session);
    }

    @Override
    public void start(String view) throws Exception {

        File file = Paths.get(Settings.getInstance().getTemplateDirectory(), Settings.getInstance().getTemplateName(), view + ".tpl").toFile();

        if (file.exists() && file.isFile()) {
            this.file = file;
            this.template = JtwigTemplate.fileTemplate(file);
            this.model = JtwigModel.newModel();

        } else {
            throw new Exception("The template view " + view + " does not exist!\nThe path: " + file.getCanonicalPath());
        }
    }

    @Override
    public void set(String name, Object value) {
        this.model.with(name, value);
    }

    public void applyGlobals() {
        this.set("site", new Site("http://localhost", "Icarus"));
        this.set("session", new IcarusSession(this.webConnection));
    }

    @Override
    public FullHttpResponse render() {
        this.applyGlobals();
        this.webConnection.setResponse(ResponseBuilder.getHtmlResponse(this.template.render(this.model)));
        return this.webConnection.response();
    }
}
