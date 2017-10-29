package org.alexdev.icarus.web.template;

import io.netty.handler.codec.http.FullHttpResponse;
import org.alexdev.icarus.duckhttpd.server.session.WebSession;
import org.alexdev.icarus.duckhttpd.template.Template;
import org.alexdev.icarus.duckhttpd.util.config.Settings;
import org.alexdev.icarus.duckhttpd.util.response.ResponseBuilder;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.File;
import java.nio.file.Paths;

public class TwigTemplate extends Template {

    private File file;
    private JtwigModel model;
    private JtwigTemplate template;

    public TwigTemplate(WebSession session) {
        super(session);
    }

    @Override
    public void start(String view) throws Exception {

        System.out.println(Settings.getInstance().getTemplateName());
        System.out.println(Settings.getInstance().getTemplateDirectory());

        File file = Paths.get(Settings.getInstance().getTemplateDirectory(), Settings.getInstance().getTemplateName(), view + ".tpl").toFile();

        if (file.exists() && file.isFile()) {
            this.file = file;
            this.template = JtwigTemplate.fileTemplate(file);
            this.model = JtwigModel.newModel();
        } else {
            throw new Exception("The template view " + view + " does not exist!");
        }
    }

    @Override
    public void set(String name, Object value) {
        this.model.with(name, value);
    }

    @Override
    public FullHttpResponse render() {
        FullHttpResponse response = ResponseBuilder.getHtmlResponse(this.template.render(this.model));
        this.webSession.setResponse(response);
        return response;
    }
}
