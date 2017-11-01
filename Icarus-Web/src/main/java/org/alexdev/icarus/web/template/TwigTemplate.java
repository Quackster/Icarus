package org.alexdev.icarus.web.template;

import io.netty.handler.codec.http.FullHttpResponse;
import org.alexdev.duckhttpd.server.connection.WebConnection;
import org.alexdev.duckhttpd.template.Template;
import org.alexdev.duckhttpd.response.ResponseBuilder;
import org.alexdev.icarus.web.template.binders.TemplateSiteBinder;
import org.alexdev.icarus.web.template.binders.TemplateSessionBinder;
import org.alexdev.icarus.web.util.config.Configuration;
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

        File file = Paths.get(Configuration.TEMPLATE_DIRECTORY, Configuration.TEMPLATE_NAME, view + ".tpl").toFile();

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
        this.set("site", new TemplateSiteBinder());
        this.set("session", new TemplateSessionBinder(this.webConnection));
    }

    @Override
    public void render() {
        this.applyGlobals();
        FullHttpResponse response = ResponseBuilder.create(this.template.render(this.model));
        this.webConnection.setResponse(response);
    }
}
