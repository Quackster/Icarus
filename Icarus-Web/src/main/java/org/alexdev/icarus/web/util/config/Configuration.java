package org.alexdev.icarus.web.util.config;

import org.ini4j.Wini;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

public class Configuration {

    private Wini configuration;

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

    public Wini values() {
        return configuration;
    }
}
