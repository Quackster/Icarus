package org.alexdev.icarus.web.template;

import io.netty.handler.codec.http.FullHttpResponse;
import org.alexdev.icarus.web.IcarusWeb;
import org.alexdev.icarus.web.util.response.WebResponse;
import org.alexdev.icarus.web.server.session.WebSession;
import org.jtwig.JtwigModel;
import org.jtwig.JtwigTemplate;

import java.io.File;
import java.nio.file.Paths;

public class Template {

    private WebSession session;
    private File file;

    private JtwigModel model;
    private JtwigTemplate template;

    public Template(WebSession session) {
        this.session = session;
    }

    public void start(String view) throws Exception {
        File file = Paths.get(IcarusWeb.getTemplateDirectory(), IcarusWeb.getTemplateName(), view + ".tpl").toFile();

        if (file.exists() && file.isFile()) {
            this.file = file;
            this.template = JtwigTemplate.fileTemplate(file);
            this.model = JtwigModel.newModel();
        } else {
            throw new Exception("The template view " + view + " does not exist!");
        }
    }

    public void set(String name, String value) {
        this.model.with(name, value);
    }

    public FullHttpResponse render() {
        return WebResponse.getHtmlResponse(this.template.render(this.model));
    }
}
