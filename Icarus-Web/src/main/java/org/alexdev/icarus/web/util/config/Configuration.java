package org.alexdev.icarus.web.util.config;

import org.alexdev.duckhttpd.util.config.Settings;
import org.alexdev.icarus.web.template.TwigTemplate;
import org.ini4j.Wini;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class Configuration {

    private Wini configuration;

    public static String TEMPLATE_NAME;
    public static String TEMPLATE_DIRECTORY;

    public void load() throws IOException {

        File file = new File("site-config.ini");
        if (!file.isFile()) {
            file.createNewFile();
            PrintWriter writer = new PrintWriter(file.getAbsoluteFile());
            writeSiteConfiguration(writer);
            writer.flush();
            writer.close();
        }

        configuration = new Wini(new File("site-config.ini"));
    }

    private static void writeSiteConfiguration(PrintWriter writer) {
        writer.println("[Directories]");
        writer.println("site.directory=tools/www");
        writer.println("template.directory=tools/www-tpl");
        writer.println();
        writer.println("[TwigTemplate]");
        writer.println("template.name=default");
        writer.println();
    }

    public void setSettings(Settings settings) {
        settings.setSiteDirectory(configuration.get("Directories", "site.directory"));
        settings.setTemplateHook(TwigTemplate.class);

        TEMPLATE_DIRECTORY = configuration.get("Directories", "template.directory");
        TEMPLATE_NAME = configuration.get("Template", "template.name");
    }
    
    public Wini values() {
        return configuration;
    }
}
